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

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
        public void testCreateNewCustomer() {
            Customer customer = new Customer();
            customer.setFirstname("John");
            customer.setLastname("Doe");
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
            customer.setFirstname("John");
            customer.setLastname("Doe");
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

            savedCustomer.setFirstname("Jane");
            savedCustomer.setLastname("Smith");

            Customer updatedCustomer = customerRepository.updateCustomer(savedCustomer.getId(), savedCustomer);

            Assertions.assertEquals("Jane", updatedCustomer.getFirstname());
            Assertions.assertEquals("Smith", updatedCustomer.getLastname());
        }

        @Test
        @Transactional
        public void testDeleteExistingCustomer() {
            Customer customer = new Customer();
            customer.setFirstname("John");
            customer.setLastname("Doe");
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
            customer.setFirstname("John");
            customer.setLastname("Doe");
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
            Assertions.assertEquals(savedCustomer.getFirstname(), foundCustomer.get().getFirstname());
            Assertions.assertEquals(savedCustomer.getLastname(), foundCustomer.get().getLastname());
            Assertions.assertEquals(savedCustomer.getEmail(), foundCustomer.get().getEmail());
        }

        @Test
        public void testFindCustomersByState() {
            Customer customer1 = new Customer();
            customer1.setFirstname("John");
            customer1.setLastname("Doe");
            customer1.setEmail("johndoe@email.com");
            customer1.setCompany("ABC Company");
            customer1.setPhone("1234567890");
            customer1.setAddress1("123 Main St");
            customer1.setAddress2("Apt 4");
            customer1.setCity("New York");
            customer1.setState("NY");
            customer1.setPostalCode("10001");
            customer1.setCountry("USA");

            Customer customer2 = new Customer();
            customer2.setFirstname("Jane");
            customer2.setLastname("Smith");
            customer2.setEmail("janesmith@email.com");
            customer2.setCompany("XYZ Inc");
            customer2.setPhone("0987654321");
            customer2.setAddress1("456 Oak St");
            customer2.setAddress2("Suite 2B");
            customer2.setCity("Los Angeles");
            customer2.setState("CA");
            customer2.setPostalCode("90001");
            customer2.setCountry("USA");

            List<Customer> customers = Arrays.asList(customer1, customer2);
            customerRepository.saveAll(customers);

            List<Customer> customersInNY = customerRepository.findCustomersByState("NY");

            Assertions.assertEquals(1, customersInNY.size());
            Assertions.assertEquals("John", customersInNY.get(0).getFirstname());
            Assertions.assertEquals("Doe", customersInNY.get(0).getLastname());
        }
    }
