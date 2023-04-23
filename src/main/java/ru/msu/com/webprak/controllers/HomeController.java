package ru.msu.com.webprak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ru.msu.com.webprak.DAO.EmployeeDAO;
import ru.msu.com.webprak.models.Employee;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @RequestMapping(value = { "/", "/employees"})
    public String index(Model model) {
        List<Employee> employee = (List<Employee>) employeeDAO.getAll();
        model.addAttribute("employee", employee);
        model.addAttribute("employeeDAO", employeeDAO);
        return "employees";
    }

    @GetMapping("/addEmployee")
    public String showAddEmployeeForm() {
        return "addEmployee";
    }
}