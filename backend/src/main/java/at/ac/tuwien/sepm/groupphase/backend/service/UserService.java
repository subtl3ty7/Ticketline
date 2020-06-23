package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * Find a application user based on the user code
     *
     * @param userCode the user code
     * @return a application user
     */
    AbstractUser findUserByUserCode(String userCode);

    /**
     * Unblock a customer based on the user code
     *
     * @param userCode the user code
     * @return user code
     */
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

    /**
     * Find all users
     * @return the list of all users
     */
    List<AbstractUser> loadAllUsers();

    /**
     * Delete a user in the database
     * @param userCode the user that should be deleted
     */
    void deleteUserByUsercode(String userCode);

    /**
     * Update a customer in the database
     * @param customer the customer that should be updated
     * @return the edited user
     */
    AbstractUser updateCustomer(Customer customer);

    /**
     * Block a customer based on the user code
     *
     * @param userCode the user code
     * @return user code
     */
    String blockCustomer(String userCode);

    /**
     * Get the current user based on authentication
     *
     * @param auth is the authentication object
     * @return the authenticated user
     */
    AbstractUser getAuthenticatedUser(Authentication auth);

    /**
     * Change password for the registered customer.
     *
     * @param email - email of the customer
     * @param newPassword - a new password
     */
    void changePasswordCustomer(String email, String newPassword);

    /**
     * Find users based on their userCode, first name, last name and email
     *
     * @param userCode - the user code to look for
     * @param firstName - first name to look for
     * @param lastName - last name to look for
     * @param email - email to look for
     * @return a list of all the users that match the given criteria
     */
    List<AbstractUser> findUserByParams(String userCode, String firstName, String lastName, String email);
}
