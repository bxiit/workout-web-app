package spring.code.restapiapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.code.restapiapp.models.BodyData;
import spring.code.restapiapp.models.Customer;
import spring.code.restapiapp.repositories.BodyDataRepository;
import spring.code.restapiapp.repositories.CustomerRepository;
import spring.code.restapiapp.util.CustomerNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BodyDataRepository bodyDataRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BodyDataRepository bodyDataRepository) {
        this.customerRepository = customerRepository;
        this.bodyDataRepository = bodyDataRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findOne(int id) {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public void save(int id, Customer customer) {
        customer.setId(id);
        customerRepository.save(customer);
    }

    @Transactional
    public void saveBodyData(int id, BodyData bodyData) {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);

        bodyData.setCustomer(customer);
        bodyDataRepository.save(bodyData);
    }

    @Transactional
    public void deleteOne(int id) {
        customerRepository.deleteById(id);
    }
}
