package org.projectAsymmetricKeys;

import org.projectAsymmetricKeys.role.Role;
import org.projectAsymmetricKeys.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.TimeZone;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final RoleRepository roleRepository){
        return args -> {
            final Optional<Role> role = roleRepository.findByName("USER_ROLE");
            if(role.isEmpty()){
                final Role role1 = new Role();
                role1.setName("ROLE_USER");
                role1.setCreatedBy("APP");
                roleRepository.save(role1);
            }
        };
    }
}
