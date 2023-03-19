package ru.msu.com.webprak.DAO;

import ru.msu.com.webprak.models.Project;
import ru.msu.com.webprak.models.Employee;

import java.util.List;

public interface ProjectDAO extends CommonDAO<Project, Long> {

    List<Project> getProjectsByName(String projectName);
    List<Employee> getEmployeesByProject(Long projectId);

}