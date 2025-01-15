package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codezx.judging.entity.Criteria;

import java.util.List;

public interface CriteriaRepository extends JpaRepository<Criteria,Long> {
    List<Criteria> findByCategoryId (long CategoryId);
}
