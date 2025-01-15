package vn.codezx.judging.service;

import vn.codezx.judging.dtos.CategoryDTO;
import vn.codezx.judging.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    List<Category> getAllCategories();

    Category getCategoryById(long id);

    Category updateCategory(long id, CategoryDTO categoryDTO);

    void deleteCategoryById(long id);
}
