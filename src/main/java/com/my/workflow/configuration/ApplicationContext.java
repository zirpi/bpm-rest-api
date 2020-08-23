package com.my.workflow.configuration;

import com.my.workflow.MyWorkflowApp;
import com.my.workflow.servicetask.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {

    @Bean
    public CheckIfCompanyIsLargeService checkIfCompanyIsLargeService(){
        return new CheckIfCompanyIsLargeService();
    }

    @Bean
    public CheckIfAddInfoRequiredService checkIfAddInfoRequiredService(){
        return new CheckIfAddInfoRequiredService();
    }

    @Bean
    public CheckClientService checkClientService(){
        return new CheckClientService();
    }

    @Bean
    public CheckIfLoanAmountSmallService checkIfLoanAmountSmallService() { return new CheckIfLoanAmountSmallService(); }

    @Bean
    public FirstLevelApprovementListener firstLevelApprovementListener() { return new FirstLevelApprovementListener(); }

    @Bean
    public EnterApprovedStateListener enterApprovedStateListener() { return new EnterApprovedStateListener(); }


    @Bean
    public EnterRejectedStateListener enterRejectedStateListener() { return new EnterRejectedStateListener(); }
}
