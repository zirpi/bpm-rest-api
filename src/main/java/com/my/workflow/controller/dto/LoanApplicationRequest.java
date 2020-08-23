package com.my.workflow.controller.dto;

import lombok.Value;

@Value
public class LoanApplicationRequest {
    private String customer;
    private String customerName;
    private String addInfo;
    private String isLarge;
    private String amount;
}
