package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
