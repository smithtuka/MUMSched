package mum.swe.mumsched.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mum.swe.mumsched.enums.RoleEnum;
import mum.swe.mumsched.model.User;

/**
 * @author Smith
 * May 10, 2020
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
	@Query("SELECT u FROM User u WHERE u.role = :role")
	List<User> findByRole(@Param("role") RoleEnum role);
}