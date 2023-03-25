package ru.msu.com.webprak.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.com.webprak.models.Employee;
import ru.msu.com.webprak.models.EmployeeOnProject;
import ru.msu.com.webprak.models.Project;
import ru.msu.com.webprak.models.PaymentHistory;
import ru.msu.com.webprak.models.PositionHistory;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class EmployeeDAOTest {
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private EmployeeOnProjectDAO employeeOnProjectDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private PaymentHistoryDAO paymentHistoryDAO;
    @Autowired
    private PositionHistoryDAO positionHistoryDAO;
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Test
    void testUpdateEmployee() {
        Employee employee = employeeDAO.getById(1L);
        assertNotNull(employee);
        employee.setName("Шелдон Кулер");
        employeeDAO.update(employee);
        employee = employeeDAO.getById(1L);
        assertEquals("Шелдон Кулер", employee.getName());
    }

    @Test
    void testDeleteEmployee() {
        Employee employee = employeeDAO.getById(1L);
        assertNotNull(employee);
        employeeDAO.delete(employee);
        employee = employeeDAO.getById(1L);
        assertNull(employee);
    }

    @Test
    void testDeleteById() {
        Employee employee = employeeDAO.getById(1L);
        assertNotNull(employee);
        employeeDAO.deleteById(1L);
        employee = employeeDAO.getById(1L);
        assertNull(employee);
    }
    
    @Test
    void testGetFilterBuilder() {
        EmployeeDAO.Filter filter = EmployeeDAO.getFilterBuilder().build();
        assertNotNull(filter);
    }

    @Test
    void testSearchEmployees() {
        EmployeeDAO.Filter.FilterBuilder filterBuilder = new EmployeeDAO.Filter.FilterBuilder();
        List<Employee> employees = employeeDAO.searchEmployees(filterBuilder.build());
        assertEquals(4, employees.size());

        EmployeeDAO.Filter.FilterBuilder filterBuilder2 = new EmployeeDAO.Filter.FilterBuilder();
        filterBuilder2.name("Шелдон Купер");
        filterBuilder2.id(1L);
        filterBuilder2.position("директор");
        filterBuilder2.projectIds(List.of(1L, 2L));
        filterBuilder2.minLengthOfService(2);
        filterBuilder2.maxLengthOfService(3);
        filterBuilder2.awardType("новый год");
        List<Employee> employees2 = employeeDAO.searchEmployees(filterBuilder2.build());
        assertEquals(1, employees2.size());

        EmployeeDAO.Filter.FilterBuilder filterBuilder3 = new EmployeeDAO.Filter.FilterBuilder();
        filterBuilder3.name("Шелдон Купер");
        List<Employee> employees3 = employeeDAO.searchEmployees(filterBuilder3.build());
        assertEquals(1, employees3.size());

        EmployeeDAO.Filter.FilterBuilder filterBuilder4 = new EmployeeDAO.Filter.FilterBuilder();
        filterBuilder4.awardType("директор");
        List<Employee> employees4 = employeeDAO.searchEmployees(filterBuilder4.build());
        System.out.println(employees4);
        assertEquals(0, employees4.size());

    }

    @Test
    void testGetPaymentHistory() {
        Employee employee = employeeDAO.getById(1L);
        List<PaymentHistory> paymentHistories = employeeDAO.getPaymentHistory(employee.getId());
        assertEquals(31, paymentHistories.size());
    }

    @Test
    void testGetPositionHistory() {
        Employee employee = employeeDAO.getById(4L);
        List<PositionHistory> positionHistories = employeeDAO.getPositionHistory(employee.getId());
        assertEquals(2, positionHistories.size());
    }

    @Test
    void testGetEmployeeProjects() {
        Employee employee = employeeDAO.getById(1L);
        List<Project> projects = employeeDAO.getEmployeeProjects(employee.getId());
        assertEquals(3, projects.size());
    }

    @Test
    void testGetAwardedPaymentHistory() {
        Employee employee = employeeDAO.getById(2L);
        List<PaymentHistory> paymentHistories = employeeDAO.getAwardedPaymentHistory(employee.getId());
        assertEquals(3, paymentHistories.size());
    }


    @BeforeEach
    void beforeEach() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "Шелдон Купер",
                "4A, 2311 North Los Robles Avenue, Pasadena, California",
                dateFormat.parse("1980-02-26"),
                "1 степень магистра, 2 докторских в Калифорнийском технологическом институте",
                "директор",  2));
        employeeList.add(new Employee(2L, "Леонард Хофстедтер",
                "4A, 2311 North Los Robles Avenue, Pasadena, California",
                dateFormat.parse("1980-05-17"),
                "Докторская степень в Калифорнийском технологическом институте",
                "заместитель директора",  2));
        employeeList.add(new Employee(3L, "Раджеш Кутраппали",
                "Raj Mahal, Pasadena, California",
                dateFormat.parse("1981-10-06"),
                "Диплом по астрофизике в Кембриджском университете, " +
                        "докторская степень в Калифорнийском технологическом институте",
                "главный аналитик",  2));
        employeeList.add(new Employee(4L, "Говард Воловиц",
                "Wolowitz House, Pasadena, California",
                dateFormat.parse("1981-03-01"),
                "Степень магистра в области инженерии Массачусетского технологического института",
                "аэрокосмический инженер",  2));
        employeeDAO.saveCollection(employeeList);

        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project(1L, "Project A", dateFormat.parse("2022-01-01"),
                dateFormat.parse("2022-11-30")));
        projectList.add(new Project(2L, "Project B", dateFormat.parse("2022-11-01"),
                dateFormat.parse("2023-01-31")));
        projectList.add(new Project(3L, "Project C", dateFormat.parse("2023-01-01"),
                null));
        projectDAO.saveCollection(projectList);

        List<EmployeeOnProject> employeeOnProjectList = new ArrayList<>();
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(1L), employeeDAO.getById(1L),
        "руководитель"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(1L), employeeDAO.getById(2L),
        "аналитик"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(1L), employeeDAO.getById(3L),
        "секретарь"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(1L), employeeDAO.getById(4L),
        "эксперт"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(2L), employeeDAO.getById(1L),
        "руководитель"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(2L), employeeDAO.getById(2L),
        "аналитик"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(2L), employeeDAO.getById(3L),
        "секретарь"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(2L), employeeDAO.getById(4L),
        "эксперт"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(3L), employeeDAO.getById(1L),
        "эксперт"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(3L), employeeDAO.getById(2L),
        "секретарь"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(3L), employeeDAO.getById(3L),
        "аналитик"));
        employeeOnProjectList.add(new EmployeeOnProject(projectDAO.getById(3L), employeeDAO.getById(4L),
        "руководитель"));
        employeeOnProjectDAO.saveCollection(employeeOnProjectList);

        List<PaymentHistory> paymentHistoryList = new ArrayList<>();
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L), "директор",
                BigDecimal.valueOf(50000.00),
                dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00),
                dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00),
                dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00),
                dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00),
                dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L), "заменитель директора",
                BigDecimal.valueOf(35000.00), dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L), "главный аналитик",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L), "инженер",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта X, руководитель",
                BigDecimal.valueOf(75000.00), dateFormat.parse("2022-11-30"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта X, аналитик",
                BigDecimal.valueOf(50000.00), dateFormat.parse("2022-11-30"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта X, секретарь",
        BigDecimal.valueOf(30000.00), dateFormat.parse("2022-11-30"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-03-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-04-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-05-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-06-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-07-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-08-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-09-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-10-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-11-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта X, эксперт",
        BigDecimal.valueOf(60000.00), dateFormat.parse("2022-11-30"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта Y, секретарь",
                BigDecimal.valueOf(25000.00), dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
        "разработка проекта Y, секретарь",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
        "разработка проекта Y, секретарь",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2023-01-31"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта Y, руководитель",
        BigDecimal.valueOf(80000.00), dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта Y, руководитель",
        BigDecimal.valueOf(80000.00), dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "разработка проекта Y, руководитель",
        BigDecimal.valueOf(80000.00), dateFormat.parse("2023-01-31"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта Y, эксперт",
                BigDecimal.valueOf(65000.00), dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта Y, эксперт",
        BigDecimal.valueOf(65000.00), dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "разработка проекта Y, эксперт",
        BigDecimal.valueOf(65000.00), dateFormat.parse("2023-01-31"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
                "разработка проекта Y, аналитик",
                BigDecimal.valueOf(55000.00), dateFormat.parse("2022-12-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта Y, аналитик",
        BigDecimal.valueOf(55000.00), dateFormat.parse("2023-01-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "разработка проекта Y, аналитик",
        BigDecimal.valueOf(55000.00), dateFormat.parse("2023-01-31"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
                "разработка проекта Z, эксперт",
        BigDecimal.valueOf(65000.00), dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
                "разработка проекта Z, секретарь",
        BigDecimal.valueOf(25000.00), dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
                "разработка проекта Z, аналитик",
        BigDecimal.valueOf(55000.00), dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
                "разработка проекта Z, руководитель",
        BigDecimal.valueOf(80000.00), dateFormat.parse("2023-02-01"), false));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
        "до 1 года", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
        "новый год", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "до 1 года", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "новый год", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "до 1 года", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "новый год", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "до 1 года", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "новый год", BigDecimal.valueOf(2000.00), dateFormat.parse("2022-12-31"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(1L),
        "день рождения", BigDecimal.valueOf(3000.00), dateFormat.parse("2022-02-26"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(2L),
        "день рождения", BigDecimal.valueOf(3000.00), dateFormat.parse("2022-05-17"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(3L),
        "день рождения", BigDecimal.valueOf(3000.00), dateFormat.parse("2022-10-06"), true));
        paymentHistoryList.add(new PaymentHistory(employeeDAO.getById(4L),
        "день рождения", BigDecimal.valueOf(3000.00), dateFormat.parse("2022-03-01"), true));
        paymentHistoryDAO.saveCollection(paymentHistoryList);

        List<PositionHistory> positionHistoryList = new ArrayList<>();
        positionHistoryList.add(new PositionHistory(employeeDAO.getById(1L),
        "директор", dateFormat.parse("2022-01-01")));
        positionHistoryList.add(new PositionHistory(employeeDAO.getById(2L),
        "заместитель директора", dateFormat.parse("2022-01-01")));
        positionHistoryList.add(new PositionHistory(employeeDAO.getById(3L),
        "главный аналитик", dateFormat.parse("2022-01-01")));
        positionHistoryList.add(new PositionHistory(employeeDAO.getById(4L),
        "аэрокосмический инженер", dateFormat.parse("2023-02-18")));
        positionHistoryList.add(new PositionHistory(employeeDAO.getById(4L),
        "инженер", dateFormat.parse("2022-01-01")));
        positionHistoryDAO.saveCollection(positionHistoryList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            // Truncate employees table and reset sequence
            entityManager.createNativeQuery("TRUNCATE employees RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE employees_employee_id_seq RESTART WITH 1;").executeUpdate();

            // Truncate position_history table and reset sequence
            entityManager.createNativeQuery("TRUNCATE position_history RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE position_history_id_seq RESTART WITH 1;").executeUpdate();

            // Truncate projects table and reset sequence
            entityManager.createNativeQuery("TRUNCATE projects RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE projects_project_id_seq RESTART WITH 1;").executeUpdate();

            // Truncate employees_on_projects table and reset sequence
            entityManager.createNativeQuery("TRUNCATE employees_on_projects RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE employees_on_projects_id_seq RESTART WITH 1;").executeUpdate();

            // Truncate payment_history table and reset sequence
            entityManager.createNativeQuery("TRUNCATE payment_history RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE payment_history_payment_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.getTransaction().commit();
        }
    }
}
