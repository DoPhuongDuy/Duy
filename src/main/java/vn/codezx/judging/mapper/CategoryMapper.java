package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.CategoryDTO;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.entity.Event;

@Component
public class CategoryMapper {

    public CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
    }
}
