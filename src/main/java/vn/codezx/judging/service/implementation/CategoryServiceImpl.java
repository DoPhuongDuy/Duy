package vn.codezx.judging.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.Exception.DataValidationException;
import vn.codezx.judging.dtos.CategoryDTO;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.mapper.CategoryMapper;
import vn.codezx.judging.repository.CategoryRepository;
import vn.codezx.judging.repository.EventRepository;
import vn.codezx.judging.service.CategoryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category Not Found with id " + id));
    }

    @Override
    public Category updateCategory(long id, CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(id);
        if(existingCategory.getName() != categoryDTO.getName()){
            if(categoryRepository.existsByName(categoryDTO.getName())){
                existingCategory.setName(categoryDTO.getName());
            }else {
                throw new DataValidationException("Category already exists");
            }
        }
        existingCategory.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    private Event findEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event Not Found with id " + eventId));
    }
}
