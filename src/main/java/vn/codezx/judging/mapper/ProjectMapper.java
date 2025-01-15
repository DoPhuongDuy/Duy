package vn.codezx.judging.mapper;

import org.springframework.stereotype.Component;
import vn.codezx.judging.dtos.ProjectDTO;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.entity.Project;

@Component
public class ProjectMapper {

    // Chuyển từ Project entity sang ProjectDTO
    public ProjectDTO toDto(Project project) {
        return ProjectDTO.builder()
                .groupName(project.getGroupName())
                .title(project.getTitle())
                .client(project.getClient())
                .eventId(project.getEvent()!=null?project.getEvent().getId():null)// Chỉ lấy ID của Category
                .description(project.getDescription())
                .build();
    }

    // Chuyển từ ProjectDTO sang Project entity
    public Project toEntity(ProjectDTO projectDTO, Event event) {
        return Project.builder()
                .groupName(projectDTO.getGroupName())
                .title(projectDTO.getTitle())
                .client(projectDTO.getClient())
                .event(event)
                .description(projectDTO.getDescription())
                .build();
    }
}
