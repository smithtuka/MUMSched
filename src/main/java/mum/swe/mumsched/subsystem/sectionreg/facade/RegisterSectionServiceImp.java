package mum.swe.mumsched.subsystem.sectionreg.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.model.Schedule;
import mum.swe.mumsched.model.Section;
import mum.swe.mumsched.service.ScheduleService;


@Service
class RegisterSectionServiceImp implements RegisterSectionService{
	@Autowired
	RegisterSectionRepository registerSectionRepo;
	@Autowired
	ScheduleService scheduleService;
	
	@Override
	public Iterable<Section> getList(){
		return registerSectionRepo.fillAllWithSort(new Sort(Direction.DESC, "id"));
	}
	
	@Override
	public Section findSectionById(Long id) {
		return registerSectionRepo.findOne(id);
	}
	
	
	@Override
	public Section save(Section Section) {
		return registerSectionRepo.save(Section);
	}

	@Override
	public Iterable<Section> getStudentSectionList() {
		return registerSectionRepo.fillAllWithSort(new Sort(Direction.DESC,""));
	}

	@Override
	public List<Section> findByEntryId(Long entryId) {
		Schedule schedule = scheduleService.findOneByEntryId(entryId);
		if(schedule != null) {
			return schedule.getBlockList().stream()
					.flatMap(block -> block.getSectionList().stream())
					.collect(Collectors.toList());
			
		}
		return new ArrayList<Section>();
	}
}
