package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.integratation.service.RemoteUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CrNoAuthController.class)
class CrNoAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionTemplate mockTransactionTemplate;
    @MockBean
    private RemoteUserService mockRemoteUserService;

    @Test
    void testAuth() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/cr/noAuth/auth")
                        .param("token", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockRemoteUserService).doAuthentication("token");
    }
}
