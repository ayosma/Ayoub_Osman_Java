package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringJUnitConfig
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void shouldCreateNewCustomer() throws Exception {
        Customer customer = new Customer("John", "Doe", "johndoe@gmail.com", "123-456-7890", "CA");
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstname\": \"John\", \"lastname\": \"Doe\", \"email\": \"johndoe@gmail.com\", \"phone\": \"123-456-7890\", \"state\": \"CA\" }"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123-456-7890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("CA"));
    }

    @Test
    public void shouldUpdateCustomerById() throws Exception {
        Customer customer = new Customer("John", "Doe", "johndoe@gmail.com", "123-456-7890", "CA");
        Optional<Customer> optionalCustomer = Optional.of(customer);
        Mockito.when(customerRepository.findById(Mockito.any(Long.class))).thenReturn(optionalCustomer);
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstname\": \"John\", \"lastname\": \"Doe\", \"email\": \"johndoe@gmail.com\", \"phone\": \"123-456-7890\", \"state\": \"CA\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123-456-7890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("CA"));
    }

    @Test
    public void shouldDeleteCustomerById() throws Exception {
        Customer customer = new Customer("John", "Doe", "johndoe@gmail.com", "123-456-7890", "CA");
        Optional<Customer> optionalCustomer = Optional.of(customer);
        Mockito.when(customerRepository.findById(Mockito.any(Long.class))).thenReturn(optionalCustomer);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(Mockito.any(Long.class));
    }

    @Test
    public void shouldGetCustomerById() throws Exception {
        Customer customer = new Customer("John", "Doe", "johndoe@gmail.com", "123-456-7890", "CA");
        Optional<Customer> optionalCustomer = Optional.of(customer);
        Mockito.when(customerRepository.findById(Mockito.any(Long.class))).thenReturn(optionalCustomer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123-456-7890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("CA"));
    }

    @Test
    public void shouldGetCustomersByState() throws Exception {
        Customer customer1 = new Customer("John", "Doe", "johndoe@gmail.com", "123-456-7890", "CA");
        Customer customer2 = new Customer("Jane", "Doe", "janedoe@gmail.com", "456-789-0123", "CA");
        Iterable<Customer> customers = Arrays.asList(customer1, customer2);
        Mockito.when(customerRepository.findByState(Mockito.any(String.class))).thenReturn(StreamSupport.stream(customers.spliterator(), false)
                .collect(Collectors.toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers?state=CA"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("johndoe@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value("123-456-7890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].state").value("CA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("janedoe@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].phone").value("456-789-0123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].state").value("CA"));
    }

}

