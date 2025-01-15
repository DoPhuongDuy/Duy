package vn.codezx.judging.controller.AdminController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.codezx.judging.dtos.PackagePlanDTO;
import vn.codezx.judging.entity.PackagePlan;
import vn.codezx.judging.service.PackagePlanService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/Admin/package-plans")
@RequiredArgsConstructor
public class PackagePlanController {

    private final PackagePlanService packagePlanService;

    @PostMapping()
    public ResponseEntity<PackagePlan> createPackagePlan(
            @Valid @RequestBody PackagePlanDTO packagePlanDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessages);
        }
        PackagePlan newPackagePlan = packagePlanService.createPackagePlan(packagePlanDTO);
        return ResponseEntity.ok(newPackagePlan);
    }

    @GetMapping()
    public ResponseEntity<List<PackagePlan>> getAllPackagePlan() {
        return ResponseEntity.ok(packagePlanService.getAllPackagePlan());
    }

    // Retrieve a role by ID
    @GetMapping("/{id}")
    public ResponseEntity<PackagePlan> getPackagePlanById(@PathVariable("id") long id) {
        PackagePlan existingPackagePlan = packagePlanService.getPackagePlanById(id);
        return ResponseEntity.ok(existingPackagePlan);
    }

    // Update an existing role
    @PutMapping("/{id}")
    public ResponseEntity<PackagePlan> updatePackagePlan(
            @PathVariable("id") long id,
            @RequestBody PackagePlanDTO packagePlanDTO) {
        PackagePlan savePackagePlan = packagePlanService.updatePackagePlanById(id,packagePlanDTO);
        return ResponseEntity.ok(savePackagePlan);
    }

    // Delete a role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable("id") long id) {
        packagePlanService.deletePackagePlanById(id);
        return ResponseEntity.ok("deleteEventById "+id);
    }
}
