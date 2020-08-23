package com.my.workflow;

//import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableProcessApplication
public class MyWorkflowApp {
    public static void main(String... args) {
        SpringApplication.run(MyWorkflowApp.class, args);
    }
}