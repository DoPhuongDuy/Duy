package vn.codezx.judging.service.implementation;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.Exception.DataValidationException;
import vn.codezx.judging.dtos.ProjectDTO;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.entity.Project;
import vn.codezx.judging.mapper.ProjectMapper;
import vn.codezx.judging.repository.CategoryRepository;
import vn.codezx.judging.repository.EventRepository;
import vn.codezx.judging.repository.ProjectRepository;
import vn.codezx.judging.service.ProjectService;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final ProjectMapper projectMapper;
    private final EventRepository eventRepository;


    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    @Override
    public Project createProject(ProjectDTO projectDTO) throws Exception{
        Event event = findEventById(projectDTO.getEventId());

        Project project = projectMapper.toEntity(projectDTO, event);

        return projectRepository.save(project);
    }

    @Override
    public void uploadProjectsFromCsv(MultipartFile file) throws Exception {
        List<Project> projects = new ArrayList<>();

        if (file.isEmpty()) {
            throw new Exception("Uploaded file is empty.");
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String[] line;
            boolean isFirstLine = true;

            while ((line = reader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.length < 5) {
                    throw new DataValidationException(
                            "Invalid CSV format: Expected 10 columns but found " + line.length + " in line: " + String.join(",", line));
                }

                ProjectDTO projectDTO = parseCsvLineToProjectDTO(line);

                Event event = findEventById(projectDTO.getEventId());

                Project project = projectMapper.toEntity(projectDTO, event);
                projects.add(project);
            }
        }

        // Lưu danh sách Project vào database
        projectRepository.saveAll(projects);
    }
    // Parse một dòng CSV thành ProjectDTO
    private ProjectDTO parseCsvLineToProjectDTO(String[] line) throws Exception {
        try {
            return ProjectDTO.builder()
                    .groupName(line[0].trim())
                    .title(line[1].trim())
                    .client(line[2].trim())
                    .eventId(Integer.parseInt(line[3].trim()))
                    .description(line[4].trim())
                    .build();
        } catch (NumberFormatException e) {
            throw new DataValidationException("Invalid number format in CSV line: " + String.join(",", line));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DataNotFoundException("Missing columns in CSV line: " + String.join(",", line));
        }
    }

    @Override
    public List<ProjectDTO> getAllProjectsByEventId(long eventId) {
        Event event = findEventById(eventId);
        List<Project> projects = projectRepository.findByEvent(event);

        return projects.stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    private Event findEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event Not Found with ID: " + eventId));
    }

    @Override
    public Project getProjectById(long id) throws Exception{
        return projectRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Project Not Found with id " + id));
    }

    @Override
    public Project updateProject(long projectId, ProjectDTO projectDTO)throws Exception {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new DataNotFoundException("Project not found with ID: " + projectId));

        Event event = eventRepository.findById(projectDTO.getEventId())
                .orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + projectDTO.getEventId()));

        existingProject.setGroupName(projectDTO.getGroupName());
        existingProject.setTitle(projectDTO.getTitle());
        existingProject.setClient(projectDTO.getClient());
        existingProject.setEvent(event);
        existingProject.setDescription(projectDTO.getDescription());

        return projectRepository.save(existingProject);
    }


    @Override
    public void deleteProjectById(long id) throws Exception {
        Project project = getProjectById(id);
        projectRepository.delete(project);
    }



    private Category findCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category Not Found with id " + categoryId));
    }
}
