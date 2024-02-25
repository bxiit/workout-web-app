package spring.code.restapiapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.code.restapiapp.config.UserDetailsImpl;
import spring.code.restapiapp.models.User;
import spring.code.restapiapp.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserDetailsImpl::new) // кастим ретурн до CustomerDetails ведь метод возвращает UserDetails (CustomerDetails реализует UserDetails)
                .orElseThrow( () -> new UsernameNotFoundException(username + " not found!") );
    }
}