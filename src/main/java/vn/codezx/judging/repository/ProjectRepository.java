package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByEvent(Event event);
    boolean existsByEvent(Event event);
}
