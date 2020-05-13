package mum.swe.mumsched.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mum.swe.mumsched.enums.MonthEnum;

/**
 * @author Smith
 * @date May 10, 2020
 */
@Entity
@Table(name = "faculties")
public class Faculty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private User user;

	@Enumerated(EnumType.STRING)
	@ElementCollection
	private List<MonthEnum> monthEnums;
	
	@ManyToMany()
	@JoinTable(name = "faculty_course", joinColumns = @JoinColumn(name = "faculty_id"), 
		inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses;
	
	@ManyToMany(mappedBy = "facultyList", cascade = CascadeType.ALL)
	private Set<Entry> entryList;
	
	@Column(nullable = false, columnDefinition = "int default 4")
	private int numberOfSectionPerEntry;

	public int getNumberOfSectionPerEntry() {
		return numberOfSectionPerEntry;
	}

	public void setNumberOfSectionPerEntry(int numberOfSectionPerEntry) {
		this.numberOfSectionPerEntry = numberOfSectionPerEntry;
	}

	public List<MonthEnum> getMonthEnums() {
		return monthEnums;
	}

	public void setMonthEnums(List<MonthEnum> monthEnums) {
		this.monthEnums = monthEnums;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
	private Set<Section> sectionList = new HashSet<Section>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Entry> getEntryList() {
		return entryList;
	}

	public void setEntryList(Set<Entry> entryList) {
		this.entryList = entryList;
	}
}
