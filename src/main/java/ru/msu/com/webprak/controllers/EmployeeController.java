package ru.msu.com.webprak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.com.webprak.DAO.EmployeeDAO;
import ru.msu.com.webprak.DAO.PaymentHistoryDAO;
import ru.msu.com.webprak.DAO.PositionHistoryDAO;
import ru.msu.com.webprak.DAO.impl.EmployeeDAOImpl;
import ru.msu.com.webprak.DAO.impl.PaymentHistoryDAOImpl;
import ru.msu.com.webprak.DAO.impl.PositionHistoryDAOImpl;
import ru.msu.com.webprak.models.Employee;
import ru.msu.com.webprak.models.PaymentHistory;
import ru.msu.com.webprak.models.PositionHistory;
import ru.msu.com.webprak.models.Project;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {
    @Autowired
    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Autowired
    private final PositionHistoryDAO positionHistoryDAO = new PositionHistoryDAOImpl();

    @Autowired
    private final PaymentHistoryDAO paymentHistoryDAO = new PaymentHistoryDAOImpl();

    @GetMapping("/employees")
    public String getEmployees(Model model) {
        List<Employee> employee = (List<Employee>) employeeDAO.getAll();
        model.addAttribute("employee", employee);
        model.addAttribute("employeeDAO", employeeDAO);
        return "employees";
    }

    @GetMapping("/addEmployee")
    public String showAddEmployeeForm() {
        return "addEmployee";
    }

    @GetMapping("/searchEmployee")
    public String searchEmployee(@RequestParam(required = false) Long id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String position,
                                 @RequestParam(required = false) String projectIds,
                                 @RequestParam(required = false) Integer minLengthOfService,
                                 @RequestParam(required = false) Integer maxLengthOfService,
                                 @RequestParam(required = false) String awardType,
                                 Model model) {

        List<Long> projectIdList = null;
        if (projectIds != null && !projectIds.isEmpty()) {
            projectIdList = Arrays.stream(projectIds.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }

        if (name != null && name.isEmpty()) {
            name = null;
        }
        if (position != null && position.isEmpty()) {
            position = null;
        }
        if (awardType != null && awardType.isEmpty()) {
            awardType = null;
        }

        EmployeeDAO.Filter filter = EmployeeDAO.getFilterBuilder()
                .id(id)
                .name(name)
                .position(position)
                .projectIds(projectIdList)
                .minLengthOfService(minLengthOfService)
                .maxLengthOfService(maxLengthOfService)
                .awardType(awardType)
                .build();

        List<Employee> employees = employeeDAO.searchEmployees(filter);

        model.addAttribute("employee", employees);
        model.addAttribute("employeeDAO", employeeDAO);
        return "employees";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@RequestParam String name,
                              @RequestParam String homeAddress,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfBirth,
                              @RequestParam String education,
                              @RequestParam String position,
                              @RequestParam Integer lengthOfService,
                              RedirectAttributes redirectAttributes) {
        Employee newEmployee = new Employee(name, homeAddress, dayOfBirth, education, position, lengthOfService);
        Employee savedEmployee = employeeDAO.save(newEmployee);

        PositionHistory newPositionHistory = new PositionHistory(savedEmployee, position, new Date());
        positionHistoryDAO.save(newPositionHistory);

        redirectAttributes.addFlashAttribute("message", "Employee added successfully!");

        return "redirect:/employees";
    }

    @GetMapping("/employeeDetails")
    public String personPage(@RequestParam(name = "employeeId") Long employeeId, Model model) {
        Employee employee = employeeDAO.getById(employeeId);
        List<Project> projects = employeeDAO.getEmployeeProjects(employeeId);
        List<PaymentHistory> paymentHistories = employeeDAO.getPaymentHistory(employeeId);
        List<PositionHistory> positionHistories = employeeDAO.getPositionHistory(employeeId);

        if (employee == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + employeeId);
            return "errorPage";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("projects", projects);
        model.addAttribute("paymentHistories", paymentHistories);
        model.addAttribute("positionHistories", positionHistories);
        return "employeeDetails";
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Employee employee = employeeDAO.getById(id);

        if (employee == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to delete employee with ID = " + id);
            return "redirect:/employees";
        }

        employeeDAO.delete(employee);

        redirectAttributes.addFlashAttribute("message", "Employee deleted successfully!");
        return "redirect:/employees";
    }

    @PostMapping("/editEmployee")
    public String editEmployee(@RequestParam Long id,
                               @RequestParam String name,
                               @RequestParam String homeAddress,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfBirth,
                               @RequestParam String education,
                               @RequestParam String position,
                               @RequestParam Integer lengthOfService,
                               RedirectAttributes redirectAttributes) {
        Employee employee = employeeDAO.getById(id);

        if (employee == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to edit employee with ID = " + id);
            return "redirect:/employees";
        }

        String oldPosition = employee.getPosition();

        employee.setName(name);
        employee.setHomeAddress(homeAddress);
        employee.setDayOfBirth(dayOfBirth);
        employee.setEducation(education);
        employee.setPosition(position);
        employee.setLengthOfService(lengthOfService);

        Employee updatedEmployee = employeeDAO.update(employee);
        if (!oldPosition.equals(position)) {
            PositionHistory newPositionHistory = new PositionHistory(updatedEmployee, position, new Date());
            positionHistoryDAO.save(newPositionHistory);
        }
        System.out.println(positionHistoryDAO.getAll());

        redirectAttributes.addFlashAttribute("message", "Employee updated successfully!");
        return "redirect:/employees";
    }

    @PostMapping("/addPaymentHistory")
    public String addPaymentHistory(@RequestParam Long employeeId,
                                    @RequestParam Double amount,
                                    @RequestParam String type,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfPayment,
                                    @RequestParam(required = false) Boolean isAward,
                                    RedirectAttributes redirectAttributes) {
        Employee employee = employeeDAO.getById(employeeId);

        if (employee == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to add payment history for employee with ID = " + employeeId);
            return "redirect:/employees";
        }

        if (isAward == null) {
            isAward = false;
        }

        BigDecimal paymentAmount = BigDecimal.valueOf(amount);
        PaymentHistory newPaymentHistory = new PaymentHistory(employee, type, paymentAmount, dateOfPayment, isAward);
        paymentHistoryDAO.save(newPaymentHistory);

        redirectAttributes.addFlashAttribute("message", "Payment history added successfully!");
        return "redirect:/employeeDetails?employeeId=" + employeeId;
    }


}
