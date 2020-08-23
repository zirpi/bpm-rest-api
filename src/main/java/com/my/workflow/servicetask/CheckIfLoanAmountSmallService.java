package com.my.workflow.servicetask;

import com.my.workflow.dataaccess.entity.LoanApplication;
import com.my.workflow.service.LoanApplicationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CheckIfLoanAmountSmallService extends AbstractService {
/*
    @Autowired
    private LoanApplicationService loanApplicationService;

    public void execute(DelegateExecution delegate) throws Exception {
        final Long applicationId = (Long) delegate.getVariable("applicationId");
        final LoanApplication application = loanApplicationService.getById(applicationId);

        final boolean isSmallAmount = application.getAmount().compareTo( new BigDecimal(1001)) < 0;
        delegate.setVariable("isSmallAmount", isSmallAmount );

        application.setCurrentStep(this.getClass().getName());
        application.setVariables(delegate.getVariables().toString());
        loanApplicationService.update(application);
    }*/

    @Override
    protected void executeImpl(DelegateExecution delegate, LoanApplication application) {

        final boolean isSmallAmount = application.getAmount().compareTo( new BigDecimal(1001)) < 0;
        delegate.setVariable("isSmallAmount", isSmallAmount );
    }
}
