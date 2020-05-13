//package mum.swe.mumsched.repository;
//
//import static org.junit.Assert.assertEquals;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import mum.swe.mumsched.MumschedApplication;
//import mum.swe.mumsched.enums.RoleEnum;
//import mum.swe.mumsched.model.User;
//
//@RunWith(SpringRunner.class)
////@JdbcTest
////@SpringBootTest
//@DataJpaTest
////@Transactional(propagation = Propagation.NOT_SUPPORTED)
////@AutoConfigureTestDatabase(replace=Replace.NONE)
////@ContextConfiguration(classes = {MumschedApplication.class})
////@AutoConfigureTestEntityManager 
////@Transactional(propagation = Propagation.NOT_SUPPORTED)
//public class UserRepositoryTests {
//
//    @Autowired
//    private TestEntityManager entityManager;
//    @Autowired
//    private UserRepository userRepository;
//
//	@Before
//	public void setup() {
//		//entityManager = 
//		
//	}
//	
//    @Test
//    public void testFindByUsername()  throws Exception{
//
//		User user = new User();
//		user.setUsername("test_user1@mum.edu");
//		user.setFirstName("User1");
//		user.setLastName("Test");
//		user.setPassword("1");
//		user.setPasswordConfirm("1");
//		user.setRole(RoleEnum.ROLE_STUDENT);
//		
//        entityManager.persist(user);
//        User userDb = userRepository.findByUsername("test_user1@mum.edu");
//        assertEquals("test_user1@mum.edu", userDb.getUsername());
//    }
//
//}
