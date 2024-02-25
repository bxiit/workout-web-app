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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.code.restapiapp.config.UserDetailsImpl;
import spring.code.restapiapp.dto.BodyDataDTO;
import spring.code.restapiapp.dto.UserDTO;
import spring.code.restapiapp.models.BodyData;
import spring.code.restapiapp.models.Role;
import spring.code.restapiapp.models.User;
import spring.code.restapiapp.repositories.UserRepository;
import spring.code.restapiapp.util.BodyDataNotCreatedException;
import spring.code.restapiapp.util.CustomerNotFoundException;
import spring.code.restapiapp.util.CustomerIsAlreadyExists;
import spring.code.restapiapp.util.NotCurrentCustomerException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<UserDTO> getAllCustomers() {
        return mongoTemplate.findAll(User.class, "customer")
                .stream().map(customer -> modelMapper.map(customer, UserDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        isCustomerExistsWithUsername(user.getUsername());
        isUserExistsWithEmail(user.getEmail());

        user.setRoles(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUpdated(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public UserDTO findCustomerByUsername(String username) {
        isCurrentCustomer(username);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomerNotFoundException("There is no a customer with this username")
        );
        if (user.getBodyData() == null) {
            return convertToUserDTO(user);
        }

        UserDTO userDTO = convertToUserDTO(user);
        BodyDataDTO bodyDataDTO = convertToBodyDataDTO(user.getBodyData());
        userDTO.setBodyData(bodyDataDTO);

        return userDTO;
    }

    @Transactional
    @Override
    public void updateCustomer(String username, User user) {
        isCurrentCustomer(username);
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("email", user.getEmail())
                .set("age", user.getAge());

        mongoTemplate.findAndModify(query, update, User.class);
    }

    @Transactional
    @Override
    public void deleteCustomer(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        mongoTemplate.remove(query, User.class);
    }
    // BODY DATA

    @Transactional
    public void addBodyData(String username, BodyData bodyData) {
        isCurrentCustomer(username);
        Optional<User> optionalCustomer = userRepository.findByUsername(username);
        User user = optionalCustomer.orElseThrow(() -> new CustomerNotFoundException("Customer doesn't exist"));
        user.setBodyData(bodyData);
        user.getBodyData().setUpdated(LocalDateTime.now());

        userRepository.save(user);
    }

    public BodyDataDTO getBodyData(String username) {
        isCurrentCustomer(username);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomerNotFoundException("Customer doesnt exist")
        );
        if (user.getBodyData() == null) {
            throw new BodyDataNotCreatedException("Body data is not created yet");
        }
        System.out.println(user.getBodyData());

        return convertToBodyDataDTO(user.getBodyData());
    }

    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public BodyDataDTO convertToBodyDataDTO(BodyData bodyData) {
        return modelMapper.map(bodyData, BodyDataDTO.class);
    }

    public BodyData convertToBodyData(BodyDataDTO bodyDataDTO) {
        return modelMapper.map(bodyDataDTO, BodyData.class);
    }

    private void isCustomerExistsWithUsername(String username) {
        Optional<User> customerOptional = userRepository.findByUsername(username);
        if (customerOptional.isPresent()) {
            throw new CustomerIsAlreadyExists("Customer with this username " + username + " already exists");
        }
    }

    private void isUserExistsWithEmail(String email) {
        Optional<User> customerOptional = userRepository.findByEmail(email);
        if (customerOptional.isPresent()) {
            throw new CustomerIsAlreadyExists("Customer with this email " + email + " already exists");
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
