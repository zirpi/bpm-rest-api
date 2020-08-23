package com.my.workflow.service;

import com.my.workflow.controller.dto.LoanApplicationRequest;
import com.my.workflow.dataaccess.LoanApplicationRepository;
import com.my.workflow.dataaccess.LoanApplicationStepRepository;
import com.my.workflow.dataaccess.entity.LoanApplication;
import com.my.workflow.dataaccess.entity.LoanApplicationStep;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository applicationRepository;

    @Autowired
    private LoanApplicationStepRepository applicationStepRepository;

    @Transactional
    public Long addLoanApplication(
            @NonNull LoanApplicationRequest request
            , @NonNull String workflowInstanceId){

        final LoanApplication application = createLoanApplication(request);
        application.setCurrentStep("created");
        application.setWorkflowInstanceId(workflowInstanceId);

        application.setId( applicationRepository.save( application ) );
        //addApplicationStep(application);
        return application.getId();
    }

    public List<LoanApplication> getAllTasks(){

        return applicationRepository.getAll()
                /*.stream()
                .map( app ->
                        LoanApplicationTask
                            .builder()
                            .id( app.getId() )
                            .workflowInstanceId( app.getWorkflowInstanceId() )
                            .customer( app.getCustomer() )
                            .customerName( app.getCustomerName() )
                            .largeCompany( app.isLargeCompany() )
                            .amount( app.getAmount() )
                            .finalDecision( app.getFinalDecision() )
                            .created( app.getCreated() )
                            .build()
                    )
                .collect(Collectors.toList())
                */
                ;
    }

    public LoanApplication getByWorkflowInstanceId(String workflowInstanceId){
        return applicationRepository.findByWorkflowInstanceId(workflowInstanceId);
    }

    public LoanApplication getById(@NonNull Long id){
        final LoanApplication app = applicationRepository.findById(id);
        app.setPerformedSteps( applicationStepRepository.getByApplicationId( app.getId() ) );
        return app;
    }

    @Transactional
    public void update(@NonNull LoanApplication application){
        applicationRepository.update( application );
        addApplicationStep( application );
    }

    private void addApplicationStep(LoanApplication app){
        final var step = new LoanApplicationStep(){{
            setApplicationId( app.getId() );
            setName( app.getCurrentStep() );
            setPerformed( LocalTime.now() );
            setVariables( app.getVariables() );
        }};
        applicationStepRepository.save(step);
    }

    private static LoanApplication createLoanApplication(LoanApplicationRequest request ) {
        return new LoanApplication() {{
            setCustomer(request.getCustomer());
            setCustomerName(request.getCustomerName());
            setLargeCompany(request.getIsLarge().equals("yes"));
            setAmount(new BigDecimal(request.getAmount()));
            setCreated(LocalTime.now());
        }};
    }
}