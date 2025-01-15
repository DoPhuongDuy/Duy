package vn.codezx.judging.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codezx.judging.dtos.CriteriaPackDTO;
import vn.codezx.judging.entity.CriteriaPack;
import vn.codezx.judging.service.CriteriaPackService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/criteria-packs")
@RequiredArgsConstructor
public class CriteriaPackController {

    private final CriteriaPackService criteriaPackService;

    // Tạo mới CriteriaPack
    @PostMapping("")
    public ResponseEntity<CriteriaPack> createCriteriaPack(@RequestBody CriteriaPackDTO criteriaPackDTO) {
        CriteriaPack criteriaPack = criteriaPackService.createCriteriaPack(criteriaPackDTO);
        return new ResponseEntity<>(criteriaPack, HttpStatus.CREATED);
    }

    // Lấy tất cả CriteriaPack theo EventId
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<CriteriaPack>> getAllCriteriaPackByEventId(@PathVariable long eventId) {
        List<CriteriaPack> criteriaPacks = criteriaPackService.getAllCriteriaPackByEventId(eventId);
        return new ResponseEntity<>(criteriaPacks, HttpStatus.OK);
    }

    // Lấy CriteriaPack theo Id
    @GetMapping("/{id}")
    public ResponseEntity<CriteriaPack> getCriteriaPackById(@PathVariable long id) {
        CriteriaPack criteriaPack = criteriaPackService.getCriteriaPackById(id);
        return new ResponseEntity<>(criteriaPack, HttpStatus.OK);
    }

    // them CriteriaPackById theo Id
    @PutMapping("/{id}")
    public ResponseEntity<CriteriaPack> updateCriteriaPackById(@PathVariable long id, @RequestBody CriteriaPackDTO criteriaPackDTO) {
        CriteriaPack updatedCriteriaPack = criteriaPackService.updateCriteriaPackById(id, criteriaPackDTO);
        return new ResponseEntity<>(updatedCriteriaPack, HttpStatus.OK);
    }

    // Xóa deleteCriteriaPackMaping theo Id
    @PutMapping("/mapping/{id}")
    public ResponseEntity<Void> deleteCriteriaPackMappingById(@PathVariable long id, @RequestBody CriteriaPackDTO criteriaPackDTO) {
        criteriaPackService.deleteCriteriaPackMapingById(id,criteriaPackDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Xóa CriteriaPack theo Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriteriaPackById(@PathVariable long id) {
        criteriaPackService.deleteCriteriaPackById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Kiểm tra nếu tồn tại CriteriaPack theo tên
    @GetMapping("/exists/{packName}")
    public ResponseEntity<Boolean> existsByPackName(@PathVariable String packName) {
        boolean exists = criteriaPackService.existsByPackName(packName);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
