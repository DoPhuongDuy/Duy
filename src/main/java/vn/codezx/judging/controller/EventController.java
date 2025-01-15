package vn.codezx.judging.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vn.codezx.judging.dtos.EventDTO;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("")
    public ResponseEntity<Event> addEvent(
            @Valid @RequestBody EventDTO eventDTO,
            BindingResult result
    ){
        // Kiểm tra nếu có lỗi trong dữ liệu đầu vào
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessages);
        }
        Event existingEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.ok(existingEvent);
    }

    @GetMapping("")
    public ResponseEntity<List<Event>> getAllEventByOrganizer(
            @Valid @RequestBody EventDTO eventDTO
    ){
        List<Event> allEventByOrganizerId = eventService.getAllEventByOrganizerId(eventDTO.getOrganizerId());
        return ResponseEntity.ok(allEventByOrganizerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(
            @PathVariable("id") int eventId
    ){
        Event existingEvent = eventService.getEventById(eventId);
        return ResponseEntity.ok(existingEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEventById(
            @Valid @RequestBody EventDTO eventDTO,
            @PathVariable("id") int eventId
    ){
        Event existingEvent = eventService.updateEventById(eventId,eventDTO);
        return ResponseEntity.ok(existingEvent);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEventById(
            @PathVariable("id") int eventId
    ){
        try{
            eventService.deleteEventById(eventId);
            return ResponseEntity.ok("deleteEventById "+eventId);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
