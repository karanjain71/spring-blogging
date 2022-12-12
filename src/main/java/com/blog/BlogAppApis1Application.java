package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepository;

@SpringBootApplication
public class BlogAppApis1Application implements CommandLineRunner{

	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApis1Application.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	 return new ModelMapper();
	}
	
	public void run(String... args) {
		try {
			Role role = new Role();
			role.setId(AppConstants.NORMAL_USER_ROLE_ID);
			role.setName("ROLE_NORMAL");
			roleRepository.save(role);
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER_ROLE_ID);
			role1.setName("ROLE_ADMIN");
			roleRepository.save(role1);
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	

}
