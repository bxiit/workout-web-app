package spring.code.restapiapp.services;

import spring.code.restapiapp.dto.UserDTO;
import spring.code.restapiapp.models.User;
import spring.code.restapiapp.registrationModel.UserRegistrationInfo;

import java.util.List;

public interface IUserService {
    void addUser(User user);
    List<UserDTO> getAllCustomers();
    UserDTO findCustomerByUsername(String customerId);
    void updateCustomer(String customerId, User user);
    void deleteCustomer(String customerId);
}
