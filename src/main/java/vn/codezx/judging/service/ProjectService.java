package vn.codezx.judging.service;

import org.springframework.web.multipart.MultipartFile;
import vn.codezx.judging.dtos.ProjectDTO;
import vn.codezx.judging.entity.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project createProject(ProjectDTO projectDTO) throws Exception;


    void uploadProjectsFromCsv(MultipartFile file) throws Exception;



    List<ProjectDTO> getAllProjectsByEventId(long eventId);

    Project getProjectById(long id) throws Exception;


    Project updateProject(long id, ProjectDTO projectDTO) throws Exception;
    void deleteProjectById(long id) throws Exception;
}
