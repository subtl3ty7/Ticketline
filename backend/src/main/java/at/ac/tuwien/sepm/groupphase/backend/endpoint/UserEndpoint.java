package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserEndpoint(UserService userService, UserMapper userMapper) {
        this.userService = userService;
       this.userMapper = userMapper;
    }

    @PostMapping(value = "/customer")
    @ApiOperation(
        value = "Register new customer",
        notes = "Register new customer in system",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 201, message = "User is successfully registered"),
        @ApiResponse(code = 400, message = "User already exists"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> registerNewCustomer(@RequestBody CustomerDto customerDto) {
        LOGGER.info("POST " + customerDto);
        Customer customer = userService.registerNewCustomer(userMapper.customerDtoToCustomer(customerDto));
        return new ResponseEntity(userMapper.customerToCustomerDto(customer), HttpStatus.CREATED);
    }
/*
    @GetMapping(value = "/all")
    @ApiOperation(
        value = "Get list of users with userCode, email, firstname, lastname, isBlocked",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "Users are successfully retrieved"),
        @ApiResponse(code = 404, message = "No Users are found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<List<SimpleUserDto>> requestAllUsers() {
        LOGGER.info("GET /api/v1/users/all");
        List<SimpleUserDto> result = userMapper.userToSimpleUserDto(userService.requestAllUsers());
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
    public ResponseEntity<SimpleUserDto> findUserByUserCode(@PathVariable String uc) {
        LOGGER.info("GET /api/v1/users/" + uc);
        SimpleUserDto result = userMapper.userToSimpleUserDto(userService.findUserByUserCode(uc));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    @ApiOperation(
        value = "Get user",
        notes = "Get user by email",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "User is successfully retrieved"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<SimpleUserDto> findUserByEmail(@PathVariable String email) {
        LOGGER.info("GET /api/v1/users/" + email);
        SimpleUserDto result = userMapper.userToSimpleUserDto(userService.findUserByEmail(email));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/admin")
    @ApiOperation(
        value = "Register new admin",
        notes = "Register new admin in system",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 201, message = "User is successfully registered"),
        @ApiResponse(code = 400, message = "User already exists"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> registerNewAdminUser(@RequestBody AdminUserDto adminUser) {
        LOGGER.info("POST /api/v1/users/admin");
        String result = userService.registerNewUser(adminUser);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping(value = "/customer")
    @ApiOperation(
        value = "Register new customer",
        notes = "Register new customer in system",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 201, message = "User is successfully registered"),
        @ApiResponse(code = 400, message = "User already exists"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> registerNewBasicUser(@RequestBody BasicUserDto basicUser) {
        LOGGER.info("GET /api/v1/users/customer");
        String result = userService.registerNewUser(basicUser);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{uc}")
    @ApiOperation(
        value = "Delete user",
        notes = "Delete user by usercode",
        authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
        @ApiResponse(code = 200, message = "User is successfully deleted"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<String> deleteUser(@PathVariable String uc) {
        LOGGER.info("GET /api/v1/users/delete/" + uc);
        String result = userService.deleteUser(uc);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<String> deleteUser(@PathVariable String uc) {
        LOGGER.info("GET /api/v1/users/block/" + uc);
        String result = userService.blockUser(uc);
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
    public ResponseEntity<String> deleteUser(@PathVariable String uc) {
        LOGGER.info("GET /api/v1/users/unblock/" + uc);
        String result = userService.unblockUser(uc);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/
}
