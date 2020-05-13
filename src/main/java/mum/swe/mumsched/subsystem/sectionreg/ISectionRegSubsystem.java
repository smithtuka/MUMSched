package mum.swe.mumsched.subsystem.sectionreg;

import java.util.List;

import mum.swe.mumsched.model.Section;
import mum.swe.mumsched.model.Student;

public interface ISectionRegSubsystem {
	List<Section> getSections(Long entryId);
	Section signInSection(Student student, Long sectionId);
	void signOutSection(Student student, Long sectionId);	
}
