package vn.codezx.judging.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.codezx.judging.Exception.DataValidationException;
import vn.codezx.judging.dtos.ProjectDTO;
import vn.codezx.judging.entity.Project;
import vn.codezx.judging.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    @GetMapping("/showAll")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }


    @PostMapping()
    public ResponseEntity<Project> addProject(
            @Valid @RequestBody ProjectDTO projectDTO,
            BindingResult result
    ) throws Exception {
        // Kiểm tra lỗi trong dữ liệu đầu vào
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new DataValidationException(errorMessages);
        }
        Project createdProject = projectService.createProject(projectDTO);
        return ResponseEntity.ok(createdProject);
    }

    @PostMapping("/uploadCSV")
    public ResponseEntity<String> uploadProjects(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty!");
        }

        try {
            // Gọi service để xử lý file CSV
            projectService.uploadProjectsFromCsv(file);
            return ResponseEntity.ok("Projects uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Project>> getAllProjectsByEventId(
            @PathVariable("eventId") int eventId
    ) {
        List<Project> projects = projectService.getAllProjectsByEventId(eventId)
                .stream()
                .map(projectDTO -> {
                    try {
                        return projectService.getProjectById(projectDTO.getEventId());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable("id") int projectId
    ) throws Exception {
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable("id") int projectId,
            @Valid @RequestBody ProjectDTO projectDTO,
            BindingResult result
    ) throws Exception {
        // Kiểm tra lỗi trong dữ liệu đầu vào
        if (result.hasErrors()) {
            String errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new Exception(errorMessages);
        }
        Project updatedProject = projectService.updateProject(projectId, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectById(
            @PathVariable("id") int projectId
    ) {
        try {
            projectService.deleteProjectById(projectId);
            return ResponseEntity.ok("Project with ID " + projectId + " has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
