package org.project.pos.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.project.pos.exception.ExceptionApp;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class CommonUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static <T> List<T> jsonArrayToList(String json, Class<T> elementClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(ArrayList.class, elementClass);
            return objectMapper.readValue(json, listType);
        }catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }

    public static <T> Optional<T> jsonToObject(String json, Class<T> elementClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            T object = objectMapper.readValue(json, elementClass);
            return Optional.of(object);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static String getAlphaNumericString(int n) {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

    public static String getTransactionId(){
        return (String) Objects.requireNonNull(RequestContextHolder.getRequestAttributes().getAttribute(ConstantApp.TRANSACTION_ID, RequestAttributes.SCOPE_REQUEST));
    }

    public static String encodeImageToBase64(MultipartFile image) {
        try {
            return Base64.getEncoder().encodeToString(image.getBytes());
        } catch (IOException e) {
            log.error("Failed to encode image to Base64 format: {}", e.getMessage());
            throw new ExceptionApp("Failed to encode image to Base64 format", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
