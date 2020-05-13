package mum.swe.mumsched.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @author Elsabeth

 * @date May 14, 2020
 */
/**
 * @author Elsabeth

 * @date May 10, 2020
 */
@Entity
@Table(name="entry")
public class Entry {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	@Size(max=100)
	private String name;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate entryDate;
	
	@Min(1)
	private int mpp;
	@Min(1)
	private int fpp;
	
	@Min(1)
	private int mppCPT;
	private int mppOPT;
	
	@Min(1)
	private int fppCPT;
	private int fppOPT;
	
	@OneToOne(mappedBy="entry")
	private Schedule schedule;
	
	@ManyToMany()
	@JoinTable(name = "entry_faculty", joinColumns = @JoinColumn(name = "entry_id"), inverseJoinColumns = @JoinColumn(name = "faculty_id"))
	private Set<Faculty> facultyList;
	
	@ManyToMany()
	@JoinTable(name = "entry_course", joinColumns = @JoinColumn(name = "entry_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courseList;
	
	@OneToMany(mappedBy="entry", cascade=CascadeType.ALL)
	private Set<Student> studentList;
	
	public String getFacutiesName() {
		return getFacultyList().stream()
				.map(m->m.getUser().getFirstName())
				.collect(Collectors.joining(", "));
	}
	
	public String getCoursesName() {
		return getCourseList().stream()
				.map(m->m.getName())
				.collect(Collectors.joining(", "));
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}
	public int getMpp() {
		return mpp;
	}
	public void setMpp(int mpp) {
		this.mpp = mpp;
	}
	public int getFpp() {
		return fpp;
	}
	public void setFpp(int fpp) {
		this.fpp = fpp;
	}
	public int getMppCPT() {
		return mppCPT;
	}
	public void setMppCPT(int mppCPT) {
		this.mppCPT = mppCPT;
	}
	public int getMppOPT() {
		return mppOPT;
	}
	public void setMppOPT(int mppOPT) {
		this.mppOPT = mppOPT;
	}
	public int getFppCPT() {
		return fppCPT;
	}
	public void setFppCPT(int fppCPT) {
		this.fppCPT = fppCPT;
	}
	public int getFppOPT() {
		return fppOPT;
	}
	public void setFppOPT(int fppOPT) {
		this.fppOPT = fppOPT;
	}

	public Set<Faculty> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(Set<Faculty> facultyList) {
		this.facultyList = facultyList;
	}

	public Set<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(Set<Course> courseList) {
		this.courseList = courseList;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Set<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(Set<Student> studentList) {
		this.studentList = studentList;
	}
}