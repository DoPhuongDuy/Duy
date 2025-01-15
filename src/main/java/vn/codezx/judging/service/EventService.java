package vn.codezx.judging.service;


import jakarta.persistence.criteria.CriteriaBuilder;
import vn.codezx.judging.dtos.EventDTO;
import vn.codezx.judging.entity.Event;

import java.util.List;


public interface EventService {
    public Event createEvent(EventDTO eventDTO);
    List<Event> getAllEventByOrganizerId(long OrganizerId);
    Event getEventById(long id);
    Event updateEventById(long id, EventDTO eventDTO);
    void deleteEventById(long id);
    boolean existsByEventName(String name);
}