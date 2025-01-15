package vn.codezx.judging.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codezx.judging.dtos.UserPackageMappingDTO;
import vn.codezx.judging.entity.UserPackageMapping;
import vn.codezx.judging.service.UserPackageMappingSerrvice;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/mapping-package")
@RequiredArgsConstructor
public class UserPackageMappingController {

    private final UserPackageMappingSerrvice userPackageMappingSerrvice;

    @GetMapping
    public ResponseEntity<List<UserPackageMapping>> getAllMappings() {
        return ResponseEntity.ok(userPackageMappingSerrvice.getAllMappings());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UserPackageMapping>> getMappingsByUserId(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(userPackageMappingSerrvice.getMappingsByUserId(userId));
    }

    @GetMapping("/package-plan/{packageId}")
    public ResponseEntity<List<UserPackageMapping>> getMappingsByPackagePlanId(@PathVariable("packageId") long PackagePlanId) {
        return ResponseEntity.ok(userPackageMappingSerrvice.getMappingsByPackagePlanId(PackagePlanId));
    }

    @PostMapping
    public ResponseEntity<UserPackageMapping> createMapping(@Valid @RequestBody UserPackageMappingDTO userPackageMappingDTO) {
        return ResponseEntity.ok(userPackageMappingSerrvice.createMapping(userPackageMappingDTO));
    }
    @PutMapping
    public ResponseEntity<UserPackageMapping> updateMapping(@Valid @RequestBody UserPackageMappingDTO userPackageMappingDTO) {
        return ResponseEntity.ok(userPackageMappingSerrvice.updateMapping(userPackageMappingDTO));
    }
    @DeleteMapping
    public ResponseEntity<String> deleteMapping(@RequestParam long userId, @RequestParam long roleId) {
        userPackageMappingSerrvice.deleteMapping(userId,roleId);
        return ResponseEntity.ok("Delete scuccessfully");
    }
}
