package spring.code.restapiapp.services;

import spring.code.restapiapp.dto.CustomerDTO;
import spring.code.restapiapp.models.Customer;

import java.util.List;

public interface ICustomerService {
    void addCustomer(Customer customer);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO findCustomerByUsername(String customerId);
    void updateCustomer(String customerId, Customer customer);
    void deleteCustomer(String customerId);
}
