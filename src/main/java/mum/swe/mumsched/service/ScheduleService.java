package mum.swe.mumsched.service;

import mum.swe.mumsched.model.Schedule;

import java.util.List;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.model.Course;
import mum.swe.mumsched.model.Entry;
import mum.swe.mumsched.model.Faculty;
/**
@author Smith

 * @date May 10, 2020
 */
public interface ScheduleService {

	Iterable<Schedule> findAll();

	Schedule findOneById(long id);

	Schedule save(Schedule item);

	Block generateBlock(MonthEnum month,int numberOfSection, List<Faculty> faculties, List<Course> courses);
	Block generateSpecificCourseBlock(MonthEnum month,int numberOfSection, List<Faculty> faculties, Course course);
	Schedule generateSchedule(Entry entry) throws Exception;

	Schedule findOneByEntryId(Long entryId);
}
