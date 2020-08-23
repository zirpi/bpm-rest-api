package com.my.workflow.dataaccess;

import com.my.workflow.dataaccess.entity.LoanApplicationStep;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoanApplicationStepRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<LoanApplicationStep> getByApplicationId(@NonNull Long applicationId){
        final var sql = "select * from LOAN_APPLICATION_STEP where application_id = ?";
        return this.jdbcTemplate.query(
                        sql,
                        new Object[]{ applicationId },
                        new BeanPropertyRowMapper<LoanApplicationStep>(LoanApplicationStep.class) );
    }

    public void save(@NonNull LoanApplicationStep step){
        final var sql = "insert into LOAN_APPLICATION_STEP (" +
                "application_id, name, performed, performed_by, variables" +
                ") values ( ?, ?, ?, ?, ? )";
        Object[] params = new Object[]{
            step.getApplicationId(),
            step.getName(),
            step.getPerformed(),
            step.getPerformedBy(),
            step.getVariables()
        };

        jdbcTemplate.update(sql, params);
    }
}
