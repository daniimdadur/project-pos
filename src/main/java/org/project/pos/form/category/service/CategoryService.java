package org.project.pos.form.category.service;

import org.project.pos.form.category.model.CategoryReq;
import org.project.pos.form.category.model.CategoryRes;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryRes> getAll();
    Optional<CategoryRes> getById(String id);
    Optional<CategoryRes> save(CategoryReq request);
    Optional<CategoryRes> update(CategoryReq request, String id);
    Optional<CategoryRes> delete(String id);
}
