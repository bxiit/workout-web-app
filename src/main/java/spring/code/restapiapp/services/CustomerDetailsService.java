package spring.code.restapiapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.code.restapiapp.config.CustomerDetails;
import spring.code.restapiapp.models.Customer;
import spring.code.restapiapp.repositories.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        return customer.map(CustomerDetails::new) // кастим ретурн до CustomerDetails ведь метод возвращает UserDetails (CustomerDetails реализует UserDetails)
                .orElseThrow( () -> new UsernameNotFoundException(username + " not found!") );
    }
}