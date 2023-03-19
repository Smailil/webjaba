package ru.msu.com.webprak.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.com.webprak.DAO.PositionHistoryDAO;
import ru.msu.com.webprak.models.PositionHistory;

@Repository
public class PositionHistoryDAOImpl extends CommonDAOImpl<PositionHistory, Long> implements PositionHistoryDAO {
    public PositionHistoryDAOImpl() {
        super(PositionHistory.class);
    }

}
