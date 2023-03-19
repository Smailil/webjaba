package ru.msu.com.webprak.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.com.webprak.DAO.EmployeeOnProjectDAO;
import ru.msu.com.webprak.models.EmployeeOnProject;

@Repository
public class EmployeeOnProjectDAOImpl extends CommonDAOImpl<EmployeeOnProject, Long> implements EmployeeOnProjectDAO {
    public EmployeeOnProjectDAOImpl() {
        super(EmployeeOnProject.class);
    }

}