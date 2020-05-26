package at.ac.tuwien.sepm.groupphase.backend.entity;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@SQLDelete(sql = "UPDATE USER SET is_Deleted=true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_Deleted=false")
@DiscriminatorValue("CUSTOMER")
public class Customer extends AbstractUser {

    @Column(name = "POINTS")
    private Long points;

    @Column(name = "IS_BLOCKED")
    private boolean isBlocked;

    public Customer() {
    }

    public Customer(String email, String password, String usercode) {
     this.setEmail(email);
     this.setPassword(password);
     this.setUserCode(usercode);
    }
    public Customer(String email, String password, String usercode, String firstName, String lastName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    @PreRemove
    public void deleteUser() {
        this.setDeleted(true);
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked){
        this.isBlocked = blocked;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public static final class CustomerBuilder {
        private Long id;
        private String userCode;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private LocalDateTime birthday;
        private boolean isLogged;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long points;
        private boolean isBlocked;

        private CustomerBuilder(){

        }

        public static Customer.CustomerBuilder aCustomer() {
            return new Customer.CustomerBuilder();
        }

        public Customer.CustomerBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Customer.CustomerBuilder withUserCode(String userCode){
            this.userCode = userCode;
            return this;
        }

        public Customer.CustomerBuilder withFirstName(String fn){
            this.firstName = fn;
            return this;
        }

        public Customer.CustomerBuilder withLastName(String ln){
            this.lastName = ln;
            return this;
        }

        public Customer.CustomerBuilder withEmail(String e){
            this.email= e;
            return this;
        }

        public Customer.CustomerBuilder withPassword(String p){
            this.password = p;
            return this;
        }

        public Customer.CustomerBuilder withBirthday(LocalDateTime bday){
            this.birthday = bday;
            return this;
        }

        public Customer.CustomerBuilder withCreatedAt(LocalDateTime ca){
            this.createdAt = ca;
            return this;
        }

        public Customer.CustomerBuilder withUpdatedAt(LocalDateTime ua){
            this.updatedAt = ua;
            return this;
        }

        public Customer.CustomerBuilder withIsLogged(boolean il){
            this.isLogged= il;
            return this;
        }

        public Customer.CustomerBuilder withIsBlocked(boolean ib){
            this.isBlocked= ib;
            return this;
        }

        public Customer.CustomerBuilder withPoints(long points){
            this.points = points;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setId(id);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setBlocked(isBlocked);
            customer.setPoints(points);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setCreatedAt(createdAt);
            customer.setUpdatedAt(updatedAt);
            customer.setUserCode(userCode);
            customer.setLogged(isLogged);
            customer.setBirthday(birthday);
            return customer;
        }

    }
}
