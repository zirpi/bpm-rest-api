package com.my.workflow.dataaccess.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationStep {
    private @Id Long id;
    private Long applicationId;
    private String name;
    private LocalTime performed;
    private String performedBy;
    private String variables;
}
