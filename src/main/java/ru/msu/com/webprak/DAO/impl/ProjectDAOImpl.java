package ru.msu.com.webprak.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.msu.com.webprak.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.msu.com.webprak.DAO.ProjectDAO;
import ru.msu.com.webprak.DAO.EmployeeOnProjectDAO;
import ru.msu.com.webprak.models.Employee;
import ru.msu.com.webprak.models.EmployeeOnProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProjectDAOImpl extends CommonDAOImpl<Project, Long> implements ProjectDAO {
    public ProjectDAOImpl() {
        super(Project.class);
    }

    @Autowired
    private EmployeeOnProjectDAO employeeOnProjectDAO = new EmployeeOnProjectDAOImpl();



    @Override
    public List<Project> getProjectsByName(String projectName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery("FROM Project WHERE name LIKE :gotName", Project.class)
                    .setParameter("gotName", "%" + projectName + "%");
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Employee> getEmployeesByProject(Long projectId) {
        List<Employee> ret = new ArrayList<>();
        for(EmployeeOnProject employeeOnProject : employeeOnProjectDAO.getAll()) {
            if (Objects.equals(employeeOnProject.getProject().getId(), projectId)) {
                ret.add(employeeOnProject.getEmployee());
            }
        }
        return ret;
    }

    @Override
    public List<EmployeeOnProject> getEmployeeOnProjectByProject(Long projectId) {
        List<EmployeeOnProject> ret = new ArrayList<>();
        for(EmployeeOnProject employeeOnProject : employeeOnProjectDAO.getAll()) {
            if (Objects.equals(employeeOnProject.getProject().getId(), projectId)) {
                ret.add(employeeOnProject);
            }
        }
        return ret;
    }

}
