package com.company.customerdataservice.repository;

import com.company.customerdataservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByState(String state);
    // Update an existing customer record
    default Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = findCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setCompany(updatedCustomer.getCompany());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setAddress1(updatedCustomer.getAddress1());
            customer.setAddress2(updatedCustomer.getAddress2());
            customer.setCity(updatedCustomer.getCity());
            customer.setState(updatedCustomer.getState());
            customer.setPostalCode(updatedCustomer.getPostalCode());
            customer.setCountry(updatedCustomer.getCountry());
            return save(customer);
        }
        return null;
    }

    // Delete an existing customer record
    void deleteCustomerById(Long id);

    // Find a customer record by id
    Optional<Customer> findCustomerById(Long id);

    // Find customer records by state
    List<Customer> findCustomersByState(String state);
}