package com.my.workflow.servicetask;

import com.my.workflow.dataaccess.entity.LoanApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;

public class LoadDataExternalCompletedListener extends AbstractService {
    @Override
    protected void executeImpl(DelegateExecution delegate, LoanApplication application) {
        // TODO

    }
}
