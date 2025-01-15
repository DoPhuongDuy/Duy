package vn.codezx.judging.service;

import vn.codezx.judging.dtos.CriteriaDTO;
import vn.codezx.judging.entity.Criteria;

import java.util.List;

public interface CriteriaService {

    // Method to create a new Criteria
    Criteria createCriteria(CriteriaDTO criteriaDTO);

    // Method to get all Criteria by Category ID
    List<Criteria> getAllCriteriaByCategoryId(long categoryId);

    // Method to get a specific Criteria by its ID
    Criteria getCriteriaById(long id);

    // Method to update an existing Criteria
    Criteria updateCriteria(long id, CriteriaDTO criteriaDTO);

    // Method to delete a Criteria by its ID
    void deleteCriteriaById(long id);
}
