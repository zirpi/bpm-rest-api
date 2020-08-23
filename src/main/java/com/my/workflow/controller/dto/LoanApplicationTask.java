package com.my.workflow.controller.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@Builder
public class LoanApplicationTask {
    private Long id;
    private String workflowInstanceId;
    private String customer;
    private String customerName;
    private boolean largeCompany;
    private BigDecimal amount;
    private LocalTime created;
    private String finalDecision;
    private Map<String, Object> variables;
}
