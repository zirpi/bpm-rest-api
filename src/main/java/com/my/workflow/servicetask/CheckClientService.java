package com.my.workflow.servicetask;

import com.my.workflow.dataaccess.entity.LoanApplication;
import com.my.workflow.service.LoanApplicationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckClientService extends  AbstractService {
    @Override
    protected void executeImpl(DelegateExecution delegate, LoanApplication application) {

        final var instanceId = delegate.getProcessInstanceId();
        application.setWorkflowInstanceId( instanceId );

        final boolean isInBlakList = ! application.getCustomer().equals("GoodCustomer");
        delegate.setVariable("isInBlackList", isInBlakList );
    }
}
