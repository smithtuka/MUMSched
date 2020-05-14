package mum.swe.mumsched.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.repository.CourseRepository;
import mum.swe.mumsched.repository.FacultyRepository;
import mum.swe.mumsched.repository.SectionRepository;
import mum.swe.mumsched.repository.StudentRepository;
import mum.swe.mumsched.service.DashboardService;

@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService {

	@Qualifier("courseRepository")
    @Autowired
    private CourseRepository courseRepository;

	@Qualifier("facultyRepository")
    @Autowired
    private FacultyRepository facultyRepository;

    @Qualifier("studentRepository")
    @Autowired
    private StudentRepository studentRepository;

	@Qualifier("sectionRepository")
    @Autowired
    private SectionRepository sectionRepository;

	@Override
	public Long countStudents() {
		return studentRepository.count();
	}

	@Override
	public Long countFaculties() {
		return facultyRepository.count();
	}

	@Override
	public Long countCourses() {
		return courseRepository.count();
	}

	@Override
	public Long countSections() {
		return sectionRepository.count();
	}

}
