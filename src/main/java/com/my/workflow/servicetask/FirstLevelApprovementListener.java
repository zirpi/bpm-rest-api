package com.my.workflow.servicetask;

import com.my.workflow.dataaccess.entity.LoanApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;

public class FirstLevelApprovementListener extends AbstractService {
    @Override
    protected void executeImpl(DelegateExecution delegate, LoanApplication application) {
        var rejectedVar = delegate.getVariable("isRejected");
        //boolean rejected = Boolean.parseBoolean(rejectedVar.toString());
        application.setCurrentStep("First Level Approvement Completed");
    }
}
