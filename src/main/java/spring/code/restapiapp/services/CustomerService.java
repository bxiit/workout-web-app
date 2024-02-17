package spring.code.restapiapp.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.code.restapiapp.dto.BodyDataDTO;
import spring.code.restapiapp.dto.CustomerDTO;
import spring.code.restapiapp.models.BodyData;
import spring.code.restapiapp.models.Customer;
import spring.code.restapiapp.repositories.CustomerRepository;
import spring.code.restapiapp.util.BodyDataNotCreatedException;
import spring.code.restapiapp.util.CustomerNotFoundException;
import spring.code.restapiapp.util.CustomerWithThisUsernameIsAlreadyExists;
import spring.code.restapiapp.util.NotCurrentCustomerException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mongoTemplate.findAll(Customer.class, "customer")
                .stream().map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public void addCustomer(Customer customer) {
        isCustomerExists(customer.getUsername());

        customer.setRoles("ROLE_USER");
        customer.setUpdated(LocalDateTime.now());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        mongoTemplate.save(customer, "customer");
    }

    @Override
    public CustomerDTO findCustomerByUsername(String username) {
        isCurrentCustomer(username);
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new CustomerNotFoundException("There is no a customer with this username")
        );
        if (customer.getBodyData() == null) {
            return convertToCustomerDTO(customer);
        }

        CustomerDTO customerDTO = convertToCustomerDTO(customer);
        BodyDataDTO bodyDataDTO = convertToBodyDataDTO(customer.getBodyData());
        customerDTO.setBodyData(bodyDataDTO);

        return customerDTO;
    }

    @Transactional
    @Override
    public void updateCustomer(String username, Customer customer) {
        isCurrentCustomer(username);
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("email", customer.getEmail())
                .set("age", customer.getAge());

        mongoTemplate.findAndModify(query, update, Customer.class);
    }

    @Transactional
    @Override
    public void deleteCustomer(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        mongoTemplate.remove(query, Customer.class);
    }

    // BODY DATA
    @Transactional
    public void addBodyData(String username, BodyData bodyData) {
        isCurrentCustomer(username);
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
        Customer customer = optionalCustomer.orElseThrow(() -> new CustomerNotFoundException("Customer doesn't exist"));
        customer.setBodyData(bodyData);
        customer.getBodyData().setUpdated(LocalDateTime.now());

        customerRepository.save(customer);
    }

    public BodyDataDTO getBodyData(String username) {
        isCurrentCustomer(username);
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new CustomerNotFoundException("Customer doesnt exist")
        );
        if (customer.getBodyData() == null) {
            throw new BodyDataNotCreatedException("Body data is not created yet");
        }
        System.out.println(customer.getBodyData());

        return convertToBodyDataDTO(customer.getBodyData());
    }


    public Customer convertToCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public CustomerDTO convertToCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public BodyDataDTO convertToBodyDataDTO(BodyData bodyData) {
        return modelMapper.map(bodyData, BodyDataDTO.class);
    }

    public BodyData convertToBodyData(BodyDataDTO bodyDataDTO) {
        return modelMapper.map(bodyDataDTO, BodyData.class);
    }

    private void isCustomerExists(String username) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if (customerOptional.isPresent()) {
            throw new CustomerWithThisUsernameIsAlreadyExists("Customer with this id already exists");
        }
    }

    private void isCurrentCustomer(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities();

        if (!username.equals(authentication.getName())) {
            throw new NotCurrentCustomerException("You can not get access to other user's data");
        }
    }
}
