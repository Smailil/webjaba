package ru.msu.com.webprak.DAO.impl;

import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.msu.com.webprak.DAO.EmployeeDAO;
import ru.msu.com.webprak.DAO.EmployeeOnProjectDAO;
import ru.msu.com.webprak.DAO.PaymentHistoryDAO;
import ru.msu.com.webprak.DAO.PositionHistoryDAO;
import ru.msu.com.webprak.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeDAOImpl extends CommonDAOImpl<Employee, Long> implements EmployeeDAO {
    public EmployeeDAOImpl() {
        super(Employee.class);
    }

    @Autowired
    private PaymentHistoryDAO paymentHistoryDAO = new PaymentHistoryDAOImpl();

    @Autowired
    private PositionHistoryDAO positionHistoryDAO = new PositionHistoryDAOImpl();

    @Autowired
    private EmployeeOnProjectDAO employeeOnProjectDAO = new EmployeeOnProjectDAOImpl();

    @Override
    public List<Employee> searchEmployees(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getId() != null)
                predicates.add(builder.equal(root.get("id"), filter.getId()));
            if (filter.getName() != null)
                predicates.add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
            if (filter.getPosition() != null)
                predicates.add(builder.like(root.get("position"), "%" + filter.getPosition() + "%"));
            if (filter.getProjectIds() != null && !filter.getProjectIds().isEmpty())
                predicates.add(root.join("projects").get("id").in(filter.getProjectIds()));
            if (filter.getMinLengthOfService() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("lengthOfService"), filter.getMinLengthOfService()));
            if (filter.getMaxLengthOfService() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("lengthOfService"), filter.getMaxLengthOfService()));
            if (filter.getAwardId() != null) {
                Join<Employee, PaymentHistory> paymentHistoryJoin = root.join("paymentHistory", JoinType.LEFT);
                predicates.add(builder.isTrue(paymentHistoryJoin.get("isAward")));
            }
            if (!predicates.isEmpty())
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<PaymentHistory> getPaymentHistory(Long employeeId) {
        List<PaymentHistory> ret = new ArrayList<>();
        for(PaymentHistory paymentHistory : paymentHistoryDAO.getAll()) {
            if (Objects.equals(paymentHistory.getEmployee().getId(), employeeId)) {
                ret.add(paymentHistory);
            }
        }
        return ret;
    }

    @Override
    public List<PaymentHistory> getAwardedPaymentHistory(Long employeeId) {
        List<PaymentHistory> ret = new ArrayList<>();
        for(PaymentHistory paymentHistory : paymentHistoryDAO.getAll()) {
            if (Objects.equals(paymentHistory.getEmployee().getId(), employeeId)) {
                if (paymentHistory.getIsAward()) {
                    ret.add(paymentHistory);
                }
            }
        }
        return ret;
    }

    @Override
    public List<PositionHistory> getPositionHistory(Long employeeId) {
        List<PositionHistory> ret = new ArrayList<>();
        for(PositionHistory positionHistory : positionHistoryDAO.getAll()) {
            if (Objects.equals(positionHistory.getEmployee().getId(), employeeId)) {
                ret.add(positionHistory);
            }
        }
        return ret;
    }

    @Override
    public List<Project> getEmployeeProjects(Long employeeId) {
        List<Project> ret = new ArrayList<>();
        for(EmployeeOnProject employeeOnProject : employeeOnProjectDAO.getAll()) {
            if (Objects.equals(employeeOnProject.getEmployee().getId(), employeeId)) {
                ret.add(employeeOnProject.getProject());
            }
        }
        return ret;
    }
}
