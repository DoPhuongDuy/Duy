package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.entity.Event;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName (String name);
}
