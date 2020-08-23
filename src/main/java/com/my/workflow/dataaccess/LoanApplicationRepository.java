package com.my.workflow.dataaccess;

import com.my.workflow.dataaccess.entity.LoanApplication;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class LoanApplicationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<LoanApplication> getAll(){
        return this.jdbcTemplate.query(
                    "select * from LOAN_APPLICATION order by id desc",
                    new BeanPropertyRowMapper<LoanApplication>(LoanApplication.class)
                );
    }

    public LoanApplication findByWorkflowInstanceId(@NonNull String workflowInstanceId){
        return this.jdbcTemplate
                .queryForObject(
                    "select * from LOAN_APPLICATION where workflow_instance_id=?"
                    , new Object[] { workflowInstanceId }
                    , new BeanPropertyRowMapper<LoanApplication>(LoanApplication.class));
    }

    public LoanApplication findById(@NonNull Long id){
        return this.jdbcTemplate
                .queryForObject(
                        "select * from LOAN_APPLICATION where id = ?"
                        , new Object[] { id }
                        , new BeanPropertyRowMapper<LoanApplication>(LoanApplication.class));
    }

    public Long save(@NonNull LoanApplication app){

        final var sql =
                "insert into LOAN_APPLICATION (" +
                "workflow_instance_id, customer, customer_name, large_company, amount, created," +
                "current_step, variables" +
                ") values (?, ?, ?, ?, ?, ?, ?, ?) ";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement upd = con.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
                        upd.setString(1, app.getWorkflowInstanceId());
                        upd.setString(2, app.getCustomer());
                        upd.setString(3, app.getCustomerName());
                        upd.setBoolean(4, app.isLargeCompany());
                        upd.setBigDecimal(5, app.getAmount());
                        upd.setObject(6, app.getCreated());
                        upd.setString(7, app.getCurrentStep());
                        upd.setString(8, app.getVariables());
                        return upd;
                    }
                }, holder );

        return holder.getKey().longValue();
    }

    public void update(@NonNull LoanApplication app){
        final var sql =
                "update LOAN_APPLICATION set " +
                "workflow_instance_id = ?, customer = ?, customer_name = ?, " +
                "large_company = ?, amount = ?, created = ?," +
                "current_step = ?, variables = ?, final_decision = ? " +
                "where id = ? ";
        Object[] params = new Object[] {
                app.getWorkflowInstanceId(),
                app.getCustomer(),
                app.getCustomerName(),
                app.isLargeCompany(),
                app.getAmount(),
                app.getCreated(),
                app.getCurrentStep(),
                app.getVariables(),
                app.getFinalDecision(),
                app.getId()
        };
        jdbcTemplate.update(sql, params);
    }
}