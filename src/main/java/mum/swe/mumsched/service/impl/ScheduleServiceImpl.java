package mum.swe.mumsched.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.enums.ScheduleStatusEnum;
import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.model.Course;
import mum.swe.mumsched.model.Entry;
import mum.swe.mumsched.model.Faculty;
import mum.swe.mumsched.model.Schedule;
import mum.swe.mumsched.model.Section;
import mum.swe.mumsched.repository.ScheduleRepository;
import mum.swe.mumsched.service.BlockService;
import mum.swe.mumsched.service.CourseService;
import mum.swe.mumsched.service.EntryService;
import mum.swe.mumsched.service.ScheduleService;

/**
 * @author Tam Huynh
 * @date May 10, 2020
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	ScheduleRepository repo;

	@Autowired
	private BlockService blockService;

	@Autowired
	EntryService entryService;

	@Autowired
	private CourseService courseService;

	@Override
	public Iterable<Schedule> findAll() {
		return repo.findAll();
	}

	@Override
	public Schedule findOneById(long id) {
		return repo.findOne(id);
	}

	@Override
	public Schedule findOneByEntryId(Long entryId) {
		// TODO Auto-generated method stub
		return repo.findOneByEntryId(entryId);
	}

	@Override
	public Schedule save(Schedule item) {
		return repo.save(item);
	}

	@Override
	public Block generateBlock(MonthEnum month, int numberOfSection, List<Faculty> listFacultyForBlock,
			List<Course> courses) {
		Block block = new Block();
		block.setMonth(month);
		List<Section> sections = new ArrayList<Section>();
		for (int i = 0; i < numberOfSection; i++) {
			Section section = new Section();
			for (int j = 0; j < courses.size(); j++) {
				Course course = courses.get(0);
				List<Faculty> listFacultyForCourse = listFacultyForBlock.stream()
						.filter(f -> f.getCourses().contains(course))
						.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry)).collect(Collectors.toList());
				if (listFacultyForCourse.size() > 0) {
					Faculty faculty = listFacultyForCourse.get(0);
					section.setCourse(course);
					section.setFaculty(faculty);
					section.setBlock(block);
					sections.add(section);
					listFacultyForBlock.remove(faculty);
					courses.remove(course);
					faculty.setNumberOfSectionPerEntry(faculty.getNumberOfSectionPerEntry() - 1);
					break;
				}
			}
		}
		block.setSectionList(new HashSet<Section>(sections));
		return block;
	}

	@Override
	public Block generateSpecificCourseBlock(MonthEnum month, int numberOfSection, List<Faculty> listFacultyForBlock,
			Course course) {
		Block block = new Block();
		block.setMonth(month);
		List<Section> sections = new ArrayList<Section>();

		for (int i = 0; i < numberOfSection; i++) {
			Section section = new Section();
			List<Faculty> listFacultyForCourse = listFacultyForBlock.stream()
					.filter(f -> f.getCourses().contains(course))
					.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry)).collect(Collectors.toList());
			if (listFacultyForCourse.size() > 0) {
				Faculty faculty = listFacultyForCourse.get(0);
				section.setCourse(course);
				section.setFaculty(faculty);
				section.setBlock(block);
				sections.add(section);
				listFacultyForBlock.remove(faculty);
				faculty.setNumberOfSectionPerEntry(faculty.getNumberOfSectionPerEntry() - 1);
			}
		}
		block.setSectionList(new HashSet<Section>(sections));
		return block;
	}

	MonthEnum month;

	@Override
	public Schedule generateSchedule(Entry entry) throws Exception {
		Schedule schedule = new Schedule();
		schedule.setStatus(ScheduleStatusEnum.DRAFT);
		schedule.setEntry(entry);
		int maxEnrollment = 25;

		List<Course> courseList = entry.getCourseList().stream().sorted(Comparator.comparing(Course::getCode))
				.collect(Collectors.toList());

		List<Faculty> facultyList = entry.getFacultyList().stream().collect(Collectors.toList());
		int fppCpt = entry.getFppCPT();
		int fppOpt = entry.getFppOPT();
		int mppCpt = entry.getMppCPT();
		int mppOpt = entry.getMppOPT();

		// Calculate number of Blocks
		int numberOfBlocks = 0;

		if (fppOpt > 0) {
			numberOfBlocks = 7;
		} else if (fppCpt > 0 || mppOpt > 0) {
			numberOfBlocks = 6;
		} else {
			numberOfBlocks = 5;
		}

		if (numberOfBlocks == 0)
			throw new Exception("Error while generating schedule");

		int numberOfFppSection = (int) (Math.ceil((double) (fppCpt + fppOpt) / (double) maxEnrollment));
		int numberOfMppSection = (int) (Math.ceil((double) (mppCpt + mppOpt) / (double) maxEnrollment));

		int numberOfTotalSectionsFirst5Blocks = numberOfFppSection + numberOfMppSection; // every first 5 block
		int numberOfTotalSections6ThBlock = (int) (Math
				.ceil((double) (fppCpt + fppOpt + mppOpt) / (double) maxEnrollment));// 6th block
		int numberOfTotalSections7ThBlock = (int) (Math.ceil((double) fppOpt / (double) maxEnrollment)); // 7th block

		// separate courses 400 and 500 credit
		List<Course> courseList400 = courseList.stream().filter(c -> c.getCode().startsWith("CS4"))
				.collect(Collectors.toList());
		List<Course> courseList500 = courseList.stream().filter(c -> c.getCode().startsWith("CS5"))
				.collect(Collectors.toList());

		// start generate block
		LinkedHashSet<Block> blocks = new LinkedHashSet<Block>();
		
		// Start B1
		month = MonthEnum.getMonth(entry.getEntryDate().getMonthValue());

		List<Faculty> facultiesB1 = facultyList.stream().filter(f -> f.getMonthEnums().contains(month.nextMonth()))
				.filter(f -> f.getNumberOfSectionPerEntry() > 0)
				.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry).reversed()).collect(Collectors.toList());
		Block B1FPP = this.generateSpecificCourseBlock(month, numberOfFppSection, facultiesB1,
				courseService.findOneByCode("CS390"));
		Block B1MPP = this.generateSpecificCourseBlock(month, numberOfMppSection, facultiesB1,
				courseService.findOneByCode("CS401"));
		Block B1 = blockService.combine2Blocks(B1FPP, B1MPP);
		B1.setSchedule(schedule);
		blocks.add(B1);
		
		// Start B2
		month = month.nextMonth();
		List<Faculty> facultiesB2 = facultyList.stream().filter(f -> f.getMonthEnums().contains(month))
				.filter(f -> f.getNumberOfSectionPerEntry() > 0)
				.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry).reversed()).collect(Collectors.toList());

		Block B2FPP = this.generateSpecificCourseBlock(month, numberOfFppSection, facultiesB2,
				courseService.findOneByCode("CS401"));
		Block B2MPP = this.generateBlock(month, numberOfMppSection, facultiesB2, courseList);

		Block B2 = blockService.combine2Blocks(B2FPP, B2MPP);
		B2.setSchedule(schedule);
		blocks.add(B2);
		
		// Start B3
		month = month.nextMonth();
		List<Faculty> facultiesB3 = facultyList.stream().filter(f -> f.getMonthEnums().contains(month))
				.filter(f -> f.getNumberOfSectionPerEntry() > 0)
				.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry)).collect(Collectors.toList());
		Block B3 = this.generateBlock(month, numberOfFppSection + numberOfMppSection, facultiesB3, courseList);
		B3.setSchedule(schedule);
		blocks.add(B3);
		// Start B4
		month = month.nextMonth();
		List<Faculty> facultiesB4 = facultyList.stream().filter(f -> f.getMonthEnums().contains(month))
				.filter(f -> f.getNumberOfSectionPerEntry() > 0)
				.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry)).collect(Collectors.toList());
		Block B4 = this.generateBlock(month, numberOfFppSection + numberOfMppSection, facultiesB4, courseList);
		B4.setSchedule(schedule);
		blocks.add(B4);
		// Start B5
		month = month.nextMonth();
		List<Faculty> facultiesB5 = facultyList.stream().filter(f -> f.getMonthEnums().contains(month))
				.filter(f -> f.getNumberOfSectionPerEntry() > 0)
				.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry)).collect(Collectors.toList());
		Block B5 = this.generateBlock(month, numberOfFppSection + numberOfMppSection, facultiesB5, courseList);
		B5.setSchedule(schedule);
		blocks.add(B5);
		
		if(numberOfTotalSections6ThBlock>0) {
			// Start B6
			month = month.nextMonth();
			List<Faculty> facultiesB6 = facultyList.stream().filter(f -> f.getMonthEnums().contains(month))
					.filter(f -> f.getNumberOfSectionPerEntry() > 0)
					.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry)).collect(Collectors.toList());
			Block B6 = this.generateBlock(month, numberOfTotalSections6ThBlock, facultiesB6, courseList);
			B6.setSchedule(schedule);
			blocks.add(B6);
		}
		
		if(numberOfTotalSections7ThBlock>0) {
			// Start B7
			month = month.nextMonth();
			List<Faculty> facultiesB7 = facultyList.stream().filter(f -> f.getMonthEnums().contains(month))
					.filter(f -> f.getNumberOfSectionPerEntry() > 0)
					.sorted(Comparator.comparing(Faculty::getNumberOfSectionPerEntry)).collect(Collectors.toList());
			Block B7 = this.generateBlock(month, numberOfTotalSections7ThBlock, facultiesB7, courseList);
			B7.setSchedule(schedule);
			blocks.add(B7);
		}
		// final
		
		schedule.setBlockList(new LinkedHashSet<Block>(blocks));
		return schedule;
	}

}
