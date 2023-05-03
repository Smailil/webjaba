package ru.msu.com.webprak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.com.webprak.DAO.*;
import ru.msu.com.webprak.DAO.impl.*;
import ru.msu.com.webprak.models.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjectController {
    @Autowired
    private final ProjectDAO projectDAO = new ProjectDAOImpl();

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private final EmployeeOnProjectDAO employeeOnProjectDAO = new EmployeeOnProjectDAOImpl();

    @GetMapping("/projects")
    public String getProjects(Model model) {
        List<Project> project = (List<Project>) projectDAO.getAll();
        model.addAttribute("project", project);
        model.addAttribute("projectDAO", projectDAO);
        return "projects";
    }

    @GetMapping("/addProject")
    public String showAddProjectForm() {
        return "addProject";
    }

    @PostMapping("/addProject")
    public String addProject(@RequestParam String name,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                             RedirectAttributes redirectAttributes) {
        Project newProject = new Project(name, startDate);
        Project savedProject = projectDAO.save(newProject);

        if (endDate != null) {
            savedProject.setEndDate(endDate);
            projectDAO.update(savedProject);
        }
        redirectAttributes.addFlashAttribute("message", "Project added successfully!");

        return "redirect:/projects";
    }

    @GetMapping("/searchProject")
    public String searchProject(@RequestParam(required = false) String name,
                                 Model model) {

        List<Project> project = projectDAO.getProjectsByName(name);
        model.addAttribute("project", project);
        return "projects";
    }

    @GetMapping("/projectDetails")
    public String projectPage(@RequestParam(name = "projectId") Long projectId, Model model) {
        Project project = projectDAO.getById(projectId);
        List<Employee> employees = projectDAO.getEmployeesByProject(projectId);
        List<EmployeeOnProject> employeeOnProjects = projectDAO.getEmployeeOnProjectByProject(projectId);

        if (project == null) {
            model.addAttribute("error_msg", "No project found with ID = " + projectId);
            return "errorPage";
        }

        model.addAttribute("project", project);
        model.addAttribute("employees", employees);
        model.addAttribute("employeeOnProjects", employeeOnProjects);
        return "projectDetails";
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Project project = projectDAO.getById(id);

        if (project == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to delete project with ID = " + id);
            return "redirect:/projects";
        }

        projectDAO.delete(project);

        redirectAttributes.addFlashAttribute("message", "Project deleted successfully!");
        return "redirect:/projects";
    }

    @PostMapping("/editProject")
    public String editProject(@RequestParam Long id,
                              @RequestParam String name,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> endDate,
                              RedirectAttributes redirectAttributes) {
        Project project = projectDAO.getById(id);

        if (project == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to edit project with ID = " + id);
            return "redirect:/projects";
        }

        project.setName(name);
        project.setStartDate(startDate);
        project.setEndDate(endDate.orElse(null));

        projectDAO.update(project);

        redirectAttributes.addFlashAttribute("message", "Project updated successfully!");
        return "redirect:/projects";
    }

    @PostMapping("/addEmployeeToProject")
    public String addEmployeeToProject(@RequestParam Long projectId,
                                       @RequestParam Long employeeId,
                                       @RequestParam String role,
                                       RedirectAttributes redirectAttributes) {
        Project project = projectDAO.getById(projectId);
        Employee employee = employeeDAO.getById(employeeId);

        if (project == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to find project with ID = " + projectId);
            return "redirect:/projects";
        }

        if (employee == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to find employee with ID = " + employeeId);
            return "redirect:/employees";
        }

        // Assuming you have a method to add an employee to a project in your ProjectDAO or a related DAO.
        EmployeeOnProject employeeOnProject = new EmployeeOnProject(project, employee, role);
        employeeOnProjectDAO.save(employeeOnProject);

        redirectAttributes.addFlashAttribute("message", "Employee added to the project successfully!");
        return "redirect:/projectDetails?projectId=" + projectId;
    }

    @PostMapping("/deleteEmployeeFromProject")
    public String deleteEmployeeFromProject(@RequestParam Long projectId,
                                            @RequestParam Long employeeOnProjectId,
                                            RedirectAttributes redirectAttributes) {
        EmployeeOnProject employeeOnProject = employeeOnProjectDAO.getById(employeeOnProjectId);

        if (employeeOnProject == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to find EmployeeOnProject with ID = " + employeeOnProjectId);
            return "redirect:/projects";
        }

        employeeOnProjectDAO.delete(employeeOnProject);

        redirectAttributes.addFlashAttribute("message", "Employee removed from the project successfully!");
        return "redirect:/projectDetails?projectId=" + projectId;
    }

}
