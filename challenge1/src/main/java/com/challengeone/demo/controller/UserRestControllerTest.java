package com.challengeone.demo.controller;

import com.challengeone.demo.repository.UsersRepository;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UsersRestController usersRestController;

    @Mock
    private UsersRepository usersRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(usersRestController).build();
    }

    @Test
    public void invalidSortFilter() throws Exception {
        mockMvc.perform(get(
                   "/users?sortBy=abc&start=0&limit=10")
                   .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidFilterChain() throws Exception {
        mockMvc.perform(get(
                   "/users?sortBy=name&start=0&limit=10&filter=age=20")
                   .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
               .andExpect(status().isBadRequest());
    }

    @Test
    public void checkForValidResponse() throws Exception {
        Mockito.when(usersRepository.findAll(Mockito.any(Specification.class))).thenReturn(Collections.emptyList());
        mockMvc.perform(get(
                   "/users?sortBy=name&start=0&limit=-1")
                   .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk());
    }
}
