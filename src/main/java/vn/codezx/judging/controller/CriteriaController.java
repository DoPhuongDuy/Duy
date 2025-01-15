package vn.codezx.judging.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codezx.judging.dtos.CriteriaDTO;
import vn.codezx.judging.entity.Criteria;
import vn.codezx.judging.service.CriteriaService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/criteria")
@RequiredArgsConstructor
public class CriteriaController {

    private final CriteriaService criteriaService;

    // Create a new Criteria
    @PostMapping
    public ResponseEntity<Criteria> createCriteria(@RequestBody CriteriaDTO criteriaDTO) {
        Criteria createdCriteria = criteriaService.createCriteria(criteriaDTO);
        return ResponseEntity.ok(createdCriteria);
    }

    // Get all Criteria by Category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Criteria>> getAllCriteriaByCategoryId(@PathVariable long categoryId) {
        List<Criteria> criteriaList = criteriaService.getAllCriteriaByCategoryId(categoryId);
        return ResponseEntity.ok(criteriaList);
    }

    // Get a Criteria by ID
    @GetMapping("/{id}")
    public ResponseEntity<Criteria> getCriteriaById(@PathVariable long id) {
        Criteria criteria = criteriaService.getCriteriaById(id);
        return ResponseEntity.ok(criteria);
    }

    // Update a Criteria by ID
    @PutMapping("/{id}")
    public ResponseEntity<Criteria> updateCriteria(@PathVariable long id, @RequestBody CriteriaDTO criteriaDTO) {
        Criteria updatedCriteria = criteriaService.updateCriteria(id, criteriaDTO);
        return ResponseEntity.ok(updatedCriteria);
    }

    // Delete a Criteria by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriteriaById(@PathVariable long id) {
        criteriaService.deleteCriteriaById(id);
        return ResponseEntity.noContent().build();
    }
}
