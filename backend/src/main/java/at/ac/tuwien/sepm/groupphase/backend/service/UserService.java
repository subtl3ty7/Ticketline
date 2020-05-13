package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the email address
     * <p>
     * For more information have a look at this tutorial:
     * https://www.baeldung.com/spring-security-authentication-with-a-database
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Find a application user based on the email address
     *
     * @param email the email address
     * @return a application user
     */
    AbstractUser findUserByEmail(String email);

    AbstractUser findUserByUserCode(String email);

    String unblockUser(String userCode);

    /**
     * Save a customer in the database
     * @param customer the customer that should be saved
     * @return the customer that was saved
     */
    Customer registerNewCustomer(Customer customer);

    /**
     * Save a admin in the database
     * @param admin the admin that should be saved
     * @return the admin that was saved
     */
    Administrator registerNewAdmin(Administrator admin);

    List<AbstractUser> loadAllUsers();

    void deleteUserByUsercode(String usercode);

    AbstractUser updateCustomer(Customer customer);

    String blockCustomer(String usercode);

    AbstractUser getAuthenticatedUser(Authentication auth);
}
