package com.company.customerdataservice.repository;

import com.company.customerdataservice.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreateNewCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@email.com");
        customer.setCompany("ABC Company");
        customer.setPhone("1234567890");
        customer.setAddress1("123 Main St");
        customer.setAddress2("Apt 4");
        customer.setCity("New York");
        customer.setState("NY");
        customer.setPostalCode("10001");
        customer.setCountry("USA");

        Customer savedCustomer = customerRepository.save(customer);

        Assertions.assertNotNull(savedCustomer.getId());
    }

    @Test
    public void testUpdateExistingCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@email.com");
        customer.setCompany("ABC Company");
        customer.setPhone("1234567890");
        customer.setAddress1("123 Main St");
        customer.setAddress2("Apt 4");
        customer.setCity("New York");
        customer.setState("NY");
        customer.setPostalCode("10001");
        customer.setCountry("USA");

        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.setFirstName("Jane");
        savedCustomer.setLastName("Smith");

        Customer updatedCustomer = customerRepository.updateCustomer(savedCustomer.getId(), savedCustomer);

        Assertions.assertEquals("Jane", updatedCustomer.getFirstName());
        Assertions.assertEquals("Smith", updatedCustomer.getLastName());
    }

    @Test
    @Transactional
    public void testDeleteExistingCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@email.com");
        customer.setCompany("ABC Company");
        customer.setPhone("1234567890");
        customer.setAddress1("123 Main St");
        customer.setAddress2("Apt 4");
        customer.setCity("New York");
        customer.setState("NY");
        customer.setPostalCode("10001");
        customer.setCountry("USA");

        Customer savedCustomer = customerRepository.save(customer);

        customerRepository.deleteCustomerById(savedCustomer.getId());

        Optional<Customer> deletedCustomer = customerRepository.findById(savedCustomer.getId());

        Assertions.assertFalse(deletedCustomer.isPresent());
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("johndoe@email.com");
        customer.setCompany("ABC Company");
        customer.setPhone("1234567890");
        customer.setAddress1("123 Main St");
        customer.setAddress2("Apt 4");
        customer.setCity("New York");
        customer.setState("NY");
        customer.setPostalCode("10001");
        customer.setCountry("USA");

        Customer savedCustomer = customerRepository.save(customer);

        Optional<Customer> foundCustomer = customerRepository.findCustomerById(savedCustomer.getId());

        Assertions.assertTrue(foundCustomer.isPresent());
        Assertions.assertEquals(savedCustomer.getFirstName(), foundCustomer.get().getFirstName());
        Assertions.assertEquals(savedCustomer.getLastName(), foundCustomer.get().getLastName());
        Assertions.assertEquals(savedCustomer.getEmail(), foundCustomer.get().getEmail());
    }

    @Test
    public void testFindCustomersByState() {
        Customer customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setState("CA");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setState("NY");
        customerRepository.save(customer2);

        List<Customer> customers = customerRepository.findByState("CA");
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
        assertEquals("Doe", customers.get(0).getLastName());
        assertEquals("CA", customers.get(0).getState());
    }
}