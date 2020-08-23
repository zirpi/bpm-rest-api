package com.my.workflow.dataaccess.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    private @Id Long id;
    private String workflowInstanceId;
    private String customer;
    private String customerName;
    private boolean largeCompany;
    private BigDecimal amount;
    private LocalTime created;
    private String finalDecision;
    private String currentStep;
    private String variables;

    @ElementCollection
    private List<LoanApplicationStep> performedSteps = new ArrayList<LoanApplicationStep>();
}
