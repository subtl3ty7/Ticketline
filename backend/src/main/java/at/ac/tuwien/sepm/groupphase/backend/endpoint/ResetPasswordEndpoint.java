package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.service.ResetPasswordService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reset-password")
public class ResetPasswordEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ResetPasswordService resetPasswordService;

    @Autowired
    public ResetPasswordEndpoint(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }


    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/{email}")
    @ApiOperation(
        value = "Reset password of user",
        notes = "Reset password of user with email")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully reset user password"),
        @ApiResponse(code = 404, message = "User is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<Void> resetPasswordRequest(@PathVariable String email) {
        LOGGER.info("GET /api/v1/reset-password/" + email);
        resetPasswordService.resetPasswordRequest(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{resetPasswordCode}")
    @ApiOperation(
        value = "Get email of reset password code",
        notes = "Get email associated with reset password code")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Code is correct"),
        @ApiResponse(code = 404, message = "Reset Password Code is not found"),
        @ApiResponse(code = 500, message = "Connection Refused"),
    })
    public ResponseEntity<UserLoginDto> getResetPasswordUserLoginDto(@PathVariable String resetPasswordCode) {
        LOGGER.info("GET /api/v1/reset-password/" + resetPasswordCode);
        String email = resetPasswordService.getResetPasswordEmailWithCode(resetPasswordCode);
        String password =resetPasswordService.generateRandomPasswordFor(email);
        UserLoginDto result =
            UserLoginDto.UserLoginDtoBuilder
                .anUserLoginDto()
                .withEmail(email)
                .withPassword(password)
                .build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Scheduled(fixedRate = 900000L)
    public void deleteExpiredResetPasswordCodes(){
        LOGGER.info("Deleting expired reset password codes");
        resetPasswordService.deleteExpiredResetPasswordCodes();
    }
}
