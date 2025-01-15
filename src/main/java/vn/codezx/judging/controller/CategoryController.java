package vn.codezx.judging.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.codezx.judging.dtos.CategoryDTO;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<Category> addCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) throws Exception {
        // Kiểm tra lỗi trong dữ liệu đầu vào
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new Exception(errorMessages);
        }
        Category createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories(
            @PathVariable("eventId") int eventId
    ) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable("id") int categoryId
    ) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("id") int categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) throws Exception {
        // Kiểm tra lỗi trong dữ liệu đầu vào
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new Exception(errorMessages);
        }
        Category updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(
            @PathVariable("id") int categoryId
    ) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok("Category with ID " + categoryId + " has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
