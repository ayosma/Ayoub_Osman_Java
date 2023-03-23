package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    @Autowired
    CustomerRepository repo;

    // Create a new customer
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return repo.save(customer);
    }
    // Find customers by state
    @GetMapping("/customer/state/{state}")
    public List<Customer> getCustomerByState(@PathVariable String state) {
        return repo.findByState(state);
    }

    // Find a customer by ID
    @GetMapping("/customer/customerId/{customerId}")
    public Customer getCustomerById(@PathVariable long customerId) {

        Optional<Customer> returnVal = repo.findById(customerId);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    // Update a customer
    @PutMapping("/customer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody Customer Customer) {
        repo.save(Customer);
    }

    // Delete a customer by ID
    @DeleteMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable long customerId) {
        repo.deleteById(customerId);
    }

}
