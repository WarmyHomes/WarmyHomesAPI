package com.project;

import com.project.entity.business.helperentity.AdvertStatusRole;
import com.project.entity.business.helperentity.TourStatusRole;
import com.project.entity.enums.AdvertStatusType;
import com.project.entity.enums.RoleType;
import com.project.entity.enums.TourStatus;
import com.project.entity.user.UserRole;
import com.project.payload.request.user.UserRequest;
import com.project.repository.business.AdvertStatusRepository;
import com.project.repository.business.TourStatusRepository;
import com.project.repository.user.UserRoleRepository;
import com.project.service.business.AdvertStatusService;
import com.project.service.business.TourStatusService;
import com.project.service.user.UserRoleService;
import com.project.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class WarmyHomesApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;

    private final UserRoleRepository userRoleRepository;

    private final UserService userService;
    private final TourStatusRepository tourStatusRepository;
    private final TourStatusService tourStatusService;
    private final AdvertStatusRepository advertStatusRepository;
    private final AdvertStatusService advertStatusService;



    public WarmyHomesApplication(UserRoleService userRoleService,
                                 UserRoleRepository userRoleRepository,
                                 UserService userService,
                                 TourStatusService tourStatusService,
                                 TourStatusRepository tourStatusRepository,
                                 AdvertStatusService advertStatusService,
                                 AdvertStatusRepository advertStatusRepository) {

        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.tourStatusRepository = tourStatusRepository;
        this.tourStatusService = tourStatusService;
        this.advertStatusRepository = advertStatusRepository;
        this.advertStatusService = advertStatusService;

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

        if (userService.countAllManagers() == 0) {

            Set<String> roles = new HashSet<>();
            roles.add("Manager");
            UserRequest managerRequest = new UserRequest();
            managerRequest.setEmail("denememanager@github.com");
            managerRequest.setPassword_hash("123456789");
            managerRequest.setFirst_name("Deneme");
            managerRequest.setLast_name("Api");
            managerRequest.setPhone("530-000-0000");
            userService.saveManager(managerRequest);



        }   

        if (tourStatusService.getAllStatus().isEmpty()){
            TourStatusRole pending = new TourStatusRole();
            pending.setTourStatus(TourStatus.PENDING);
            pending.setStatusName("Pending");

            tourStatusRepository.save(pending);

            TourStatusRole declined = new TourStatusRole();
            declined.setTourStatus(TourStatus.DECLINED);
            declined.setStatusName("Rejected");
            tourStatusRepository.save(declined);


            TourStatusRole approved = new TourStatusRole();
            approved.setTourStatus(TourStatus.APPROVED);
            approved.setStatusName("Approved");
            tourStatusRepository.save(approved);

            TourStatusRole canceled = new TourStatusRole();
            canceled.setTourStatus(TourStatus.CANCELED);
            canceled.setStatusName("Canceled");
            tourStatusRepository.save(canceled);
        }

        if (advertStatusRepository.findAll().isEmpty()){
            AdvertStatusRole pending = new AdvertStatusRole();
            pending.setAdvertStatusType(AdvertStatusType.PENDING);
            pending.setAdvertStatusId(0);
            advertStatusRepository.save(pending);

            AdvertStatusRole activated = new AdvertStatusRole();
            activated.setAdvertStatusType(AdvertStatusType.ACTIVATED);
            activated.setAdvertStatusId(1);
            advertStatusRepository.save(activated);

            AdvertStatusRole rejected = new AdvertStatusRole();
            rejected.setAdvertStatusType(AdvertStatusType.REJECTED);
            rejected.setAdvertStatusId(2);
            advertStatusRepository.save(rejected);

        }

    }


}
