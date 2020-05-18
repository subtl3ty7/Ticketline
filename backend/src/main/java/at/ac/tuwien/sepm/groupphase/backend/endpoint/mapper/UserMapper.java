package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Administrator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper
public interface UserMapper {

    @Named("userDtoToCustomer")
    Customer userDtoToCustomer(UserDto userDto);

    @IterableMapping(qualifiedByName = "userDtoToCustomer")
    List<Customer> userDtoToCustomer(List<UserDto> userDtos);

    @Named("userDtoToAdministrator")
    Administrator userDtoToAdministrator(UserDto userDto);

    @IterableMapping(qualifiedByName = "userDtoToAdministrator")
    List<Administrator> userDtoToAdministrator(List<UserDto> userDtos);

    @Named("abstractUserToUserDto")
    @Mapping(source = "user", target = "admin", qualifiedByName = "setAdmin")
    @Mapping(source = "user", target = "blocked", qualifiedByName = "setBlocked")
    @Mapping(source = "user", target = "points", qualifiedByName = "setPoints")
    UserDto abstractUserToUserDto(AbstractUser user);

    @IterableMapping(qualifiedByName = "abstractUserToUserDto")
    List<UserDto> abstractUserToUserDto(List<AbstractUser> users);

    @Named("setAdmin")
    default boolean setAdmin(AbstractUser user) {
        if(user instanceof Administrator){
            return true;
        } else {
            return false;
        }
    }

    @Named("setBlocked")
    default boolean setBlocked(AbstractUser user) {
        if(user instanceof Administrator){
            return false;
        } else {
            return ((Customer)user).isBlocked();
        }
    }

    @Named("setPoints")
    default Long setPoints(AbstractUser user) {
        if(user instanceof Administrator){
            return 0L;
        } else {
            return ((Customer)user).getPoints();
        }
    }

}
