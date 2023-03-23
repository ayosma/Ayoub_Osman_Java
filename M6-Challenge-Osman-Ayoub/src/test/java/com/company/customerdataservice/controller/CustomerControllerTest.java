package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldCreateNewCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhone("123-456-7890");
        customer.setCompany("ABC Company");
        customer.setEmail("johndoe@gmail.com");
        customer.setAddress1("123 Main St");
        customer.setAddress2("Apt 4");
        customer.setCity("New York");
        customer.setState("NY");
        customer.setPostalCode("10001");
        customer.setCountry("USA");

        String inputJson = mapper.writeValueAsString(customer);

        mockMvc.perform(post("/customer")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        Customer customer = new Customer();
        String inputJson = mapper.writeValueAsString(customer);

        mockMvc.perform(put("/customer")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    @Test
    public void shouldDeleteCustomerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/2", 2))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetCustomerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/customer/customerId/1", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCustomersByState() throws Exception {
        mockMvc.perform(get("/customer/state/NewYork"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}

