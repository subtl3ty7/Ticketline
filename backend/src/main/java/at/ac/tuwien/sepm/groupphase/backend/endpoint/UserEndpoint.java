package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final UserMapper userMapper;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserEndpoint(UserService userService, UserMapper userMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
       this.userMapper = userMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/customers")
    @ApiOperation(
        value = "Register new customer",
        notes = "Register new customer in system")
    @ApiResponses({
        @ApiResponse(code = 201, message = "User is successfully registered"),
        @ApiResponse(code = 400, message = "User already exists"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> registerNewCustomer(@RequestBody UserDto userDto) {
        LOGGER.info("POST " + userDto);
        AbstractUser customer = userService.registerNewCustomer(userMapper.userDtoToCustomer(userDto));

        ResponseEntity response = new ResponseEntity(userMapper.abstractUserToUserDto(customer), HttpStatus.CREATED);
        LOGGER.info("Sending API Response: [" + response.getBody() + ", " + response.getStatusCode() + "]");
        return response;
    }

    @GetMapping(value = "/all")
    @ApiOperation(
        value = "Get list of users.",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Users are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Users are found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<UserDto>> requestAllUsers() {
        LOGGER.info("GET /api/v1/users/all");
        List<UserDto> result = userMapper.abstractUserToUserDto(userService.loadAllUsers());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{uc}")
    @ApiOperation(
        value = "Get user",
        notes = "Get user by usercode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "User is successfully retrieved"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<UserDto> findUserByUserCode(@PathVariable String uc) {
        LOGGER.info("GET /api/v1/users/" + uc);
        UserDto result = userMapper.abstractUserToUserDto(userService.findUserByUserCode(uc));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/administrators")
    @ApiOperation(
        value = "Register new admin",
        notes = "Register new admin in system",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 201, message = "User is successfully registered"),
        @ApiResponse(code = 400, message = "User already exists"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<UserDto> registerNewAdminUser(@RequestBody UserDto userDto) {
        LOGGER.info("POST " + userDto);
        AbstractUser admin = userService.registerNewAdmin(userMapper.userDtoToAdministrator(userDto));
        ResponseEntity response = new ResponseEntity(userMapper.abstractUserToUserDto(admin), HttpStatus.CREATED);
        LOGGER.info("Sending API Response: [" + response.getBody() + ", " + response.getStatusCode() + "]");
        return response;
    }

    @DeleteMapping(value = "/{usercode}")
    @ApiOperation(
        value = "Delete user",
        notes = "Delete user by usercode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 204, message = "User is successfully deleted"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String usercode) {
        LOGGER.info("DELETE /api/v1/users/" + usercode);
        userService.deleteUserByUsercode(usercode);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value= "/update")
    @ApiOperation(
        value = "Update user",
        notes = "Update user by usercode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "User is successfully updated"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        LOGGER.info("PUT /api/v1/users/" + userDto);

        AbstractUser customer = userService.updateCustomer(userMapper.userDtoToCustomer(userDto));
        return new ResponseEntity(userMapper.abstractUserToUserDto(customer), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/block/{uc}")
    @ApiOperation(
        value = "Block a user",
        notes = "Block a user by usercode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "User is successfully blocked"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> blockUser(@PathVariable String uc) {
        LOGGER.info("GET /api/v1/users/block/" + uc);
        String result = userService.blockCustomer(uc);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/unblock/{uc}")
    @ApiOperation(
        value = "Unblock a user",
        notes = "Unblock a user by usercode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "User is successfully unblocked"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> unblockUser(@PathVariable String uc) {
        LOGGER.info("GET /api/v1/users/unblock/" + uc);
        String result = userService.unblockUser(uc);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    @ApiOperation(
        value = "Logout",
        notes = "Logout the user",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Successful logout"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<Boolean> logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        LOGGER.info("GET /api/v1/users/logout");
        new SecurityContextLogoutHandler().logout(request, response, auth);
        new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY).logout(request, response, auth);
        this.applicationEventPublisher.publishEvent(new LogoutSuccessEvent(auth));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/my-profile")
    @ApiOperation(
        value = "Return the current user",
        notes = "Return the information about current authenticated user",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully retrieved user info"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<UserDto> getAuthenticatedUser(Authentication auth) {
        LOGGER.info("GET /api/v1/users/my-profile");
        UserDto result = userMapper.abstractUserToUserDto(userService.getAuthenticatedUser(auth));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/change-password")
    @ApiOperation(
        value = "Change Password",
        notes = "Change the Password of user",
        authorizations = {@Authorization(value = "apiKey")}
    )
    @ApiResponse(code = 200, message = "Successfully retrieved user info")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        LOGGER.info("PUT /api/v1/users/change-password");
        userService.changePasswordCustomer(changePasswordDto.getEmail(), changePasswordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "", params = {"userCode", "firstName", "lastName", "email"})
    @ApiOperation(
        value = "Users with param",
        notes = "Get all users with params",
    authorizations = {@Authorization(value = "apiKey")}
    )
    @ApiResponse(code = 200, message = "Users are successfully retrieved")
    public ResponseEntity<List<AbstractUser>> findUsersbyParam(@RequestParam String userCode, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        List<UserDto> result = userMapper.abstractUserToUserDto(userService.findUserByParams(userCode, firstName, lastName, email));
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
