package at.ac.tuwien.sepm.groupphase.backend.entity;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@SQLDelete(sql = "UPDATE USER SET is_Deleted=true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_Deleted=false")
@DiscriminatorValue("ADMIN_USER")
public class Administrator extends AbstractUser {


    public Administrator() {
    }

    public Administrator(String email, String password, String usercode) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
    }
    public Administrator(String email, String password, String usercode, String firstName, String lastName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCode(usercode);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public static final class AdministratorBuilder {
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

        private AdministratorBuilder(){

        }

        public static Administrator.AdministratorBuilder aAdministrator(){
            return new Administrator.AdministratorBuilder();
        }

        public Administrator.AdministratorBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Administrator.AdministratorBuilder withUserCode(String userCode){
            this.userCode = userCode;
            return this;
        }

        public Administrator.AdministratorBuilder withFirstName(String fn){
            this.firstName = fn;
            return this;
        }

        public Administrator.AdministratorBuilder withLastName(String ln){
            this.lastName = ln;
            return this;
        }

        public Administrator.AdministratorBuilder withEmail(String e){
            this.email= e;
            return this;
        }

        public Administrator.AdministratorBuilder withPassword(String p){
            this.password = p;
            return this;
        }

        public Administrator.AdministratorBuilder withBirthday(LocalDateTime bday){
            this.birthday = bday;
            return this;
        }

        public Administrator.AdministratorBuilder withCreatedAt(LocalDateTime ca){
            this.createdAt = ca;
            return this;
        }

        public Administrator.AdministratorBuilder withUpdatedAt(LocalDateTime ua){
            this.updatedAt = ua;
            return this;
        }

        public Administrator.AdministratorBuilder withIsLogged(boolean il){
            this.isLogged= il;
            return this;
        }

        public Administrator build() {
            Administrator administrator = new Administrator();
            administrator.setId(id);
            administrator.setFirstName(firstName);
            administrator.setLastName(lastName);
            administrator.setEmail(email);
            administrator.setPassword(password);
            administrator.setCreatedAt(createdAt);
            administrator.setUpdatedAt(updatedAt);
            administrator.setUserCode(userCode);
            administrator.setLogged(isLogged);
            administrator.setBirthday(birthday);
            return administrator;
        }

    }

}
