package com.project;

import com.project.entity.enums.RoleType;
import com.project.entity.user.UserRole;
import com.project.payload.request.user.UserRequest;
import com.project.repository.user.UserRoleRepository;
import com.project.service.user.UserRoleService;
import com.project.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class WarmyHomesApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;

    private final UserRoleRepository userRoleRepository;

    private final UserService userService;

    public WarmyHomesApplication(UserRoleService userRoleService,
                                 UserRoleRepository userRoleRepository,
                                 UserService userService) {

        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WarmyHomesApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        if (userRoleService.getAllUserRole().isEmpty()) {

            UserRole admin = new UserRole();
            admin.setRoleType(RoleType.ADMIN);
            admin.setName("Admin");

            userRoleRepository.save(admin);


            UserRole manager = new UserRole();
            manager.setRoleType(RoleType.MANAGER);
            manager.setName("Manager");
            userRoleRepository.save(manager);

            UserRole customer = new UserRole();
            customer.setRoleType(RoleType.CUSTOMER);
            customer.setName("Customer");
            userRoleRepository.save(customer);

            UserRole anonymous = new UserRole();
            anonymous.setRoleType(RoleType.ANONYMOUS);
            anonymous.setName("Anonymous");
            userRoleRepository.save(anonymous);


        }

        if (userService.countAllAdmins() == 0) {

            Set<String> roles = new HashSet<>();
            roles.add("Admin");
            UserRequest adminRequest = new UserRequest();
            adminRequest.setEmail("deneme@github.com");
            adminRequest.setPassword_hash("123456789");
            adminRequest.setFirst_name("Deneme");
            adminRequest.setLast_name("Api");
            adminRequest.setPhone("530-000-0000");
            userService.saveAdmin(adminRequest);



        }
    }
}
