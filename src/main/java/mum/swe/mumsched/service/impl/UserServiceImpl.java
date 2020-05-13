package mum.swe.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.enums.RoleEnum;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.repository.UserRepository;
import mum.swe.mumsched.service.UserService;

/**
 * @author Smith T

 * May 10, 2020

 */
@Service("userService")
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
		setUserPassword(user);
		user = userRepository.save(user);		
		return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@Override
	public List<User> findByRole(RoleEnum role) {
		return userRepository.findByRole(role);
	}

	@Override
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		userRepository.delete(id);
	}

	@Override
	public void setUserPassword(User user) {
		if(user != null) {
			if(user!=null && user.getPassword() != null && !user.getPassword().isEmpty())
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			else if(user.getId() != null) {
				User userdb = userRepository.findOne(user.getId());
				user.setPassword(userdb.getPassword());
			}
		}
	}
}
