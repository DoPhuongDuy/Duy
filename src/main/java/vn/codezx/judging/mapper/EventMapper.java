package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.EventDTO;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.entity.PackagePlan;
import vn.codezx.judging.entity.User;

@Component
public class EventMapper {

    public EventDTO toDto(Event event) {
        return EventDTO.builder()
                .eventName(event.getEventName())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .description(event.getDescription())
                .schoolId(event.getSchoolId())
                .organizerId(event.getOrganizerId() != null ? event.getOrganizerId().getId() : null)
                .planid(event.getPackagePlan().getId())
                .build();
    }

    public Event toEntity(EventDTO eventDTO, User organizer, PackagePlan packagePlan) {
        return Event.builder()
                .eventName(eventDTO.getEventName())
                .startDate(eventDTO.getStartDate())
                .endDate(eventDTO.getEndDate())
                .description(eventDTO.getDescription())
                .schoolId(eventDTO.getSchoolId())
                .organizerId(organizer)
                .packagePlan(packagePlan)
                .build();
    }
}

