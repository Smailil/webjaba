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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ProjectDAOTest {
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private EmployeeOnProjectDAO employeeOnProjectDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void testGetProjectsByName() {
        List<Project> projects = projectDAO.getProjectsByName("Project");
        assertEquals(3, projects.size());

        List<Project> projects1 = projectDAO.getProjectsByName("Project A");
        assertEquals(1, projects1.size());

        List<Project> projects2 = projectDAO.getProjectsByName("Project Baza");
        assertNull(projects2);
    }

    @Test
    void testGetEmployeesByProject() {
        Project project = projectDAO.getById(2L);
        List<Employee> employees = projectDAO.getEmployeesByProject(project.getId());
        assertEquals(4, employees.size());
    }



    @BeforeEach
    void beforeEach() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "Шелдон Купер",
                "4A, 2311 North Los Robles Avenue, Pasadena, California",
                dateFormat.parse("1980-02-26"),
                "1 степень магистра, 2 докторских в Калифорнийском технологическом институте",
                "директор", 2));
        employeeList.add(new Employee(2L, "Леонард Хофстедтер",
                "4A, 2311 North Los Robles Avenue, Pasadena, California",
                dateFormat.parse("1980-05-17"),
                "Докторская степень в Калифорнийском технологическом институте",
                "заместитель директора", 2));
        employeeList.add(new Employee(3L, "Раджеш Кутраппали",
                "Raj Mahal, Pasadena, California",
                dateFormat.parse("1981-10-06"),
                "Диплом по астрофизике в Кембриджском университете, " +
                        "докторская степень в Калифорнийском технологическом институте",
                "главный аналитик", 2));
        employeeList.add(new Employee(4L, "Говард Воловиц",
                "Wolowitz House, Pasadena, California",
                dateFormat.parse("1981-03-01"),
                "Степень магистра в области инженерии Массачусетского технологического института",
                "аэрокосмический инженер", 2));
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
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            // Truncate employees table and reset sequence
            entityManager.createNativeQuery("TRUNCATE employees RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE employees_employee_id_seq RESTART WITH 1;").executeUpdate();

            // Truncate projects table and reset sequence
            entityManager.createNativeQuery("TRUNCATE projects RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE projects_project_id_seq RESTART WITH 1;").executeUpdate();

            // Truncate employees_on_projects table and reset sequence
            entityManager.createNativeQuery("TRUNCATE employees_on_projects RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE employees_on_projects_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.getTransaction().commit();
        }
    }
}
