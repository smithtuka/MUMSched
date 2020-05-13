package mum.swe.mumsched.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import mum.swe.mumsched.enums.RoleEnum;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.repository.UserRepository;
import mum.swe.mumsched.service.UserService;

//@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc()
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@EnableAutoConfiguration

//@Configuration
//@SpringBootApplication
//@ComponentScan("mum.swe.mumsched")

@RunWith(SpringRunner.class)
@SpringBootTest
//@SpringBootTest
//@WebAppConfiguration
//@WebMvcTest(UserController.class)
public class UserControllerTests {

	@Autowired
	private WebApplicationContext context;

//	@Autowired
//	private ApplicationContext context;
	
	//@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
/*	@MockBean
	private UserDetailsService userDetailsService;*/
	
//	@Test
//	public void testUsers() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCreate() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void contextLoads() throws Exception {
//		assertThat(this.context).isNotNull();
//	}
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
		
	}
	
	@Test
	public void testView() throws Exception {

		User user = new User();
		user.setUsername("test_user1@mum.edu");
		user.setFirstName("User1");
		user.setLastName("Test");
		user.setPassword("1");
		user.setPasswordConfirm("1");
		user.setRole(RoleEnum.ROLE_STUDENT);
		
		
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.toString()));
//        
//		Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString()))
//			.thenReturn(new org.springframework.security.core.userdetails.User("admin@mum.edu", "1", grantedAuthorities));
		Mockito.when(userService.findOne(Mockito.anyLong())).thenReturn(user);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get("/user/1")
					.with(SecurityMockMvcRequestPostProcessors.user("admin@mum.edu").password("1").roles("ADMIN"));

		mockMvc.perform(requestBuilder)
				
				.andExpect(status().isOk())
				.andExpect(model().attribute("user", user))
				.andExpect( view().name("user/edit"));
	}

//	@Test
//	public void testEdit() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDelete() {
//		fail("Not yet implemented");
//	}

}
