package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
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

    // Find all customers
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return repo.findAll();
    }

    // Create a new customer
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return repo.save(customer);
    }

    // Find a customer by ID
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Optional<Customer> customer = repo.findById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok().body(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a customer by ID
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") Long id, @RequestBody Customer customerDetails) {
        Optional<Customer> customer = repo.findById(id);
        if (customer.isPresent()) {
            Customer updatedCustomer = customer.get();
            updatedCustomer.setFirstname(customerDetails.getFirstname());
            updatedCustomer.setLastname(customerDetails.getLastname());
            updatedCustomer.setEmail(customerDetails.getEmail());
            updatedCustomer.setPhone(customerDetails.getPhone());
            updatedCustomer.setState(customerDetails.getState());
            repo.save(updatedCustomer);
            return ResponseEntity.ok().body(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a customer by ID
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("id") Long id) {
        Optional<Customer> customer = repo.findById(id);
        if (customer.isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Find customers by state
    @GetMapping("/customers/state/{state}")
    public List<Customer> getCustomersByState(@PathVariable("state") String state) {
        return repo.findByState(state);
    }
}
