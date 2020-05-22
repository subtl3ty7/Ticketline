package at.ac.tuwien.sepm.groupphase.backend.service.exceptionhandler;

import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.util.ValidationMessage;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;

import java.lang.invoke.MethodHandles;

@Configuration
@Aspect
public class ServiceAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /*
    @AfterThrowing(
        value = "execution(* at.ac.tuwien.sepm.groupphase.backend.service.*.*(..))",
        throwing = "e"
    )
    public void handleDataAccess(DataAccessException e) throws ServiceException {
        LOGGER.debug("Handling Data Integrity Violation Exception");
        throw new ServiceException(ValidationMessage.defaultDataAccessMessage, e);
    }

    @AfterThrowing(
        value = "execution(* at.ac.tuwien.sepm.groupphase.backend.service.*.*(..))",
        throwing = "e"
    )
    public void handleValidation(ValidationException e) throws ServiceException {
        LOGGER.debug("Handling Validation Exception");
        throw new ServiceException(e.getValidationMessage().getMessages(), e);
    }
    */




}
