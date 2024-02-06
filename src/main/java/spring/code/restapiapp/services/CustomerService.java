package spring.code.restapiapp.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.code.restapiapp.dto.CustomerDTO;
import spring.code.restapiapp.models.BodyData;
import spring.code.restapiapp.models.Customer;
import spring.code.restapiapp.repositories.BodyDataRepository;
import spring.code.restapiapp.repositories.CustomerRepository;
import spring.code.restapiapp.util.CustomerNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BodyDataRepository bodyDataRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public List<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .stream().map(this::convertToCustomerDTO)
                .toList();
    }

    public CustomerDTO findOne(int id) {
        return convertToCustomerDTO(customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new));
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
    public void addAdmin(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setCreated(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Transactional
    public void addAdmin(Customer customer, String roles) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRoles(roles);
        customer.setCreated(LocalDateTime.now());
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

    public Customer convertToCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public CustomerDTO convertToCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
