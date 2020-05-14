package mum.swe.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.model.Student;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.repository.StudentRepository;
import mum.swe.mumsched.service.StudentService;
import mum.swe.mumsched.service.UserService;

/**
 * @author Smith T

 * May 10, 2020
 */
@Service("studentService")
public class StudentServiceImpl  implements StudentService {

	@Qualifier("studentRepository")
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public Student save(Student student) {

    	User user = student.getUser();

    	if(student.getId() == null)
    	{
    		user = userService.save(user);
    		student.setUser(user);
    	}
    	else {
			if(user.getPasswordConfirm() != null && !user.getPasswordConfirm().isEmpty())
				user.setPassword(bCryptPasswordEncoder.encode(user.getPasswordConfirm()));
    	}
        	//userService.setUserPassword(student.getUser());	
		return studentRepository.save(student);		
    }

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student findOne(Long id) {
		return studentRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		studentRepository.delete(id);
	}

	@Override
	public Student findByUsername(String userName) {
		return studentRepository.findByUsername(userName);
	}
}
