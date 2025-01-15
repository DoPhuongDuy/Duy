package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.codezx.judging.entity.Event;
import vn.codezx.judging.entity.User;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByEventName (String eventName);

    List<Event> findByOrganizerId(User organizerId);
}
