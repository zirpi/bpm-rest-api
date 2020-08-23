package com.my.workflow.controller.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoanApplicationRequestTest {

    @Test
    public void deserializeJson() throws JsonProcessingException {
        String json =
                "{ " +
                        "\"customer\":\"GoodCustomer\"" +
                        ",\"customerName\":\"joe\"" +
                        ",\"addInfo\":\"yes\"" +
                        ",\"isLarge\":\"yes\"" +
                        ",\"amount\":\"1000\"" +
                "}";
        LoanApplicationRequest example =
                new ObjectMapper()
                        .readValue(json, LoanApplicationRequest.class);

        assertEquals("joe", example.getCustomerName());
    }
}
