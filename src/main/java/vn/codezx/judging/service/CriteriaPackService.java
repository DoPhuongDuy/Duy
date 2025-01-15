package vn.codezx.judging.service;

import vn.codezx.judging.dtos.CriteriaPackDTO;
import vn.codezx.judging.entity.CriteriaPack;

import java.util.List;
import java.util.Optional;

public interface CriteriaPackService {
    public CriteriaPack createCriteriaPack(CriteriaPackDTO criteriaPackDTO);
    List<CriteriaPack> getAllCriteriaPackByEventId(long eventId);
    public CriteriaPack getCriteriaPackById(long id);
    public CriteriaPack updateCriteriaPackById(long id, CriteriaPackDTO criteriaPackDTO);
    public void deleteCriteriaPackMapingById(long id, CriteriaPackDTO criteriaPackDTO);
    public void deleteCriteriaPackById(long id);
    public boolean existsByPackName(String packName);
}
