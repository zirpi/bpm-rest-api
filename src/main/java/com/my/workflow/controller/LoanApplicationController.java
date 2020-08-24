package com.my.workflow.controller;

import com.my.workflow.controller.dto.LoanApplicationRequest;
import com.my.workflow.dataaccess.entity.LoanApplication;
import com.my.workflow.exception.TaskApiException;
import com.my.workflow.service.LoanApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class LoanApplicationController {

    @Autowired
    LoanApplicationService loanApplicationService;

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping("/tasklist")
    public List<LoanApplication> getTasks(){
        final var applications = loanApplicationService.getAllTasks();
        final var taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();

        for ( LoanApplication app : applications ){
            String taskRef = getTaskId(taskService, app.getId().toString());
            app.setWorkflowInstanceId( taskRef );
        }
        return applications;
    }

    @GetMapping("/task/{id}")
    public LoanApplication getTask(@PathVariable Long id){
        final var app = loanApplicationService.getById(id);
        final var taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();

        // TODO: may one business case have several active tasks ?
        String taskRef = getTaskId(taskService, app.getId().toString());
        app.setWorkflowInstanceId( taskRef );

        return app;
    }

    @PostMapping("/complete/{id}")
    public void completeTask(@PathVariable Long id, @RequestBody Map<String, Object> variables){
        final LoanApplication application = loanApplicationService.getById(id);
        final TaskService taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();
        final String taskId = getTaskId(taskService, application.getId().toString());
        taskService.complete(taskId, variables);
    }

    @PostMapping("/addtask")
    public String addNewTask(@RequestBody LoanApplicationRequest request){
        try {
            final Long applicationId = loanApplicationService.addLoanApplication(request, "");

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("addinfo", request.getAddInfo());
            variables.put("applicationId", applicationId);

            final var processEngine = ProcessEngines.getDefaultProcessEngine();
            final var repositorySvc = processEngine.getRepositoryService();
            final var processDefinition = repositorySvc
                    .createProcessDefinitionQuery()
                    .list()
                    .get(0);

            final var businessKey = applicationId;
            final var instance = processEngine.getRuntimeService()
                    .startProcessInstanceByKey( processDefinition.getKey(), businessKey.toString(), variables);

            return instance.getId();
        }
        catch (Exception ex){
            // TODO: delete task from BPM engine if it was created
            logger.error(ex.getMessage() + ":" + ex.getStackTrace());
            throw new TaskApiException("Failed to save the loan application.");
        }
    }

    private static String getTaskId(TaskService taskService, String businessKey) {
        final var tasks = taskService.createTaskQuery()
                .processInstanceBusinessKey(businessKey)
                .taskAssigned()
                .active()
                .list();
        var taskRef = "";
        if (tasks.size() > 0){
            // TODO: may one business case have several active tasks ?
            final Task task = tasks.get(0);
            taskRef = task.getId();
        }
        return taskRef;
    }
}
