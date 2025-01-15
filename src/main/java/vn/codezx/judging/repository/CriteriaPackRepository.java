package vn.codezx.judging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codezx.judging.entity.CriteriaPack;

import java.util.List;

public interface CriteriaPackRepository extends JpaRepository<CriteriaPack, Long> {
    List<CriteriaPack> findByEventId(long eventId);
    boolean existsByPackName(String packName);
}
