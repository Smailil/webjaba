package ru.msu.com.webprak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ru.msu.com.webprak.DAO.DisbursementPolicyDAO;
import ru.msu.com.webprak.DAO.EmployeeDAO;
import ru.msu.com.webprak.DAO.ProjectDAO;
import ru.msu.com.webprak.models.DisbursementPolicy;
import ru.msu.com.webprak.models.Employee;
import ru.msu.com.webprak.models.Project;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private DisbursementPolicyDAO disbursementPolicyDAO;

    @RequestMapping(value = { "/", "/employees"})
    public String index(Model model) {
        List<Employee> employee = (List<Employee>) employeeDAO.getAll();
        model.addAttribute("employee", employee);
        model.addAttribute("employeeDAO", employeeDAO);
        return "employees";
    }

    @RequestMapping(value = { "/projects"})
    public String projects(Model model) {
        List<Project> project = (List<Project>) projectDAO.getAll();
        model.addAttribute("project", project);
        model.addAttribute("projectDAO", projectDAO);
        return "projects";
    }

    @RequestMapping(value = { "/disbursementPolicies"})
    public String disbursementPolicy(Model model) {
        List<DisbursementPolicy> disbursementPolicy = (List<DisbursementPolicy>) disbursementPolicyDAO.getAll();
        model.addAttribute("disbursementPolicy", disbursementPolicy);
        return "disbursementPolicies";
    }
}