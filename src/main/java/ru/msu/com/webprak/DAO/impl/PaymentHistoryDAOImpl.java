package ru.msu.com.webprak.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.com.webprak.DAO.PaymentHistoryDAO;
import ru.msu.com.webprak.models.PaymentHistory;

@Repository
public class PaymentHistoryDAOImpl extends CommonDAOImpl<PaymentHistory, Long> implements PaymentHistoryDAO {
    public PaymentHistoryDAOImpl() {
        super(PaymentHistory.class);
    }

}
