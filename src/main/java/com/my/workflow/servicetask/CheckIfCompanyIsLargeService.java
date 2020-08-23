package com.my.workflow.servicetask;

import com.my.workflow.dataaccess.entity.LoanApplication;
import com.my.workflow.service.LoanApplicationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckIfCompanyIsLargeService extends AbstractService {
/*
    @Autowired
    private LoanApplicationService loanApplicationService;

    public void execute(DelegateExecution delegate) throws Exception {
        final Long applicationId = (Long) delegate.getVariable("applicationId");
        final LoanApplication application = loanApplicationService.getById(applicationId);

        delegate.setVariable("isLarge", application.isLargeCompany() );

        application.setCurrentStep(this.getClass().getName());
        application.setVariables(delegate.getVariables().toString());
        loanApplicationService.update(application);
   }
*/
    @Override
    protected void executeImpl(DelegateExecution delegate, LoanApplication application) {
        delegate.setVariable("isLarge", application.isLargeCompany() );
    }


}
