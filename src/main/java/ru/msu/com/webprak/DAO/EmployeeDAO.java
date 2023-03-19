package ru.msu.com.webprak.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.com.webprak.models.Employee;
import ru.msu.com.webprak.models.PaymentHistory;
import ru.msu.com.webprak.models.PositionHistory;
import ru.msu.com.webprak.models.Project;

import java.util.List;

public interface EmployeeDAO extends CommonDAO<Employee, Long> {
    List<Employee> searchEmployees(Filter filter);

    List<PaymentHistory> getPaymentHistory(Long employeeId);

    List<PositionHistory> getPositionHistory(Long employeeId);

    List<Project> getEmployeeProjects(Long employeeId);

    List<PaymentHistory> getAwardedPaymentHistory(Long employeeId);

    @Builder
    @Getter
    class Filter {
        private Long id;
        private String name;
        private String position;
        private List<Long> projectIds;
        private Integer minLengthOfService;
        private Integer maxLengthOfService;
        private Long awardId;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
