package com.my.workflow.servicetask;

import com.my.workflow.dataaccess.entity.LoanApplication;
import com.my.workflow.service.LoanApplicationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class AbstractService implements JavaDelegate {

    @Autowired
    private LoanApplicationService loanApplicationService;

    public void execute(DelegateExecution delegate) throws Exception {

        final Long applicationId = (Long) delegate.getVariable("applicationId");
        final LoanApplication application = loanApplicationService.getById(applicationId);

        executeImpl(delegate, application);

        application.setCurrentStep( this.getClass().getSimpleName() );
        application.setVariables( serializeMap( delegate.getVariables() ) );
        loanApplicationService.update(application);
    }

    protected abstract void executeImpl(DelegateExecution delegate, LoanApplication application);

    private String serializeMap(Map<String, Object> map){

        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet() ){
            sb.append( entry.getKey() + ": " + entry.getValue().toString() + ";   " );
        }
        return sb.toString();
    }
}
