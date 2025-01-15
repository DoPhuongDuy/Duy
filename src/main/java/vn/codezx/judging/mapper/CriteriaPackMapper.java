package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.CriteriaPackDTO;
import vn.codezx.judging.entity.Criteria;
import vn.codezx.judging.entity.CriteriaPack;
import vn.codezx.judging.entity.Event;

import java.util.List;

@Component
public class CriteriaPackMapper {

    public CriteriaPackDTO toDto(CriteriaPack criteriaPack) {
        return CriteriaPackDTO.builder()
                .packName(criteriaPack.getPackName())
                .description(criteriaPack.getDescription())
                .eventId(criteriaPack.getEvent().getId())
                .roundNumber(criteriaPack.getRoundNumber())
                .build();
    }

    public CriteriaPack toEntity(CriteriaPackDTO criteriaPackDTO, Event event, List<Criteria> criteriaList) {
        return CriteriaPack.builder()
                .packName(criteriaPackDTO.getPackName())
                .description(criteriaPackDTO.getDescription())
                .roundNumber(criteriaPackDTO.getRoundNumber())
                .event(event)
                .criteriaList(criteriaList)
                .build();
    }
}
