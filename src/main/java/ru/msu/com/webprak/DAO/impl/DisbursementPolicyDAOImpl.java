package ru.msu.com.webprak.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.com.webprak.DAO.DisbursementPolicyDAO;
import ru.msu.com.webprak.models.DisbursementPolicy;

@Repository
public class DisbursementPolicyDAOImpl extends CommonDAOImpl<DisbursementPolicy, Long> implements DisbursementPolicyDAO {
    public DisbursementPolicyDAOImpl() {
        super(DisbursementPolicy.class);
    }

}
