package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.CriteriaDTO;
import vn.codezx.judging.entity.Criteria;
import vn.codezx.judging.entity.Category;

@Component
public class CriteriaMapper {

    public CriteriaDTO toDto(Criteria criteria) {
        return CriteriaDTO.builder()
                .criteriaName(criteria.getCriteriaName())
                .description(criteria.getDescription())
                .weight(criteria.getWeight())
                .categoryId(criteria.getCategory() != null ? criteria.getCategory().getId() : null)
                .build();
    }

    public Criteria toEntity(CriteriaDTO criteriaDTO, Category category) {
        return Criteria.builder()
                .criteriaName(criteriaDTO.getCriteriaName())
                .description(criteriaDTO.getDescription())
                .weight(criteriaDTO.getWeight())
                .category(category)
                .build();
    }
}
