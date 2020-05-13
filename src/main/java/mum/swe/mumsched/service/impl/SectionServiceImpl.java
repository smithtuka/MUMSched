package mum.swe.mumsched.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.model.Section;
import mum.swe.mumsched.repository.SectionRepository;
import mum.swe.mumsched.service.SectionService;

/**
 * @author Elsabeth

 * @date May 13, 2020

 */
@Service
public class SectionServiceImpl implements SectionService {
	@Autowired
	SectionRepository sectionRepo;
	
	@Override
	public Section findSectionById(Long id) {
		return sectionRepo.findOne(id);
	}
	
	@Override
	public Iterable<Section> getList(){
		return sectionRepo.fillAllWithSort(new Sort(Direction.ASC, "block.schedule.entry.name").and(new Sort(Direction.ASC, "block.month")));
	}
	
	@Override
	public boolean hasExistsSection(long blockId, long facultyId, long courseId, long excludedId) {
		return sectionRepo.hasExistsSection(blockId, facultyId, courseId, excludedId); 
	}
	
	@Override
	public boolean hasStudentRef(Section section) {
		return section.getStudentList().size() > 0;
	}
	
	@Override
	public Section save(Section Section) {
		return sectionRepo.save(Section);
	}
	
	@Override
	public List<Section> saveAll(List<Section> Sections) {
		return (List<Section>)sectionRepo.save(Sections);
	}
	
	@Override
	public void delete(Section Section) {
		sectionRepo.delete(Section);
	}

	@Override
	public boolean hasExistsFacultyBlock(long blockId, long facultyId, long excludedId) {
		return sectionRepo.hasExistsFacultyBlock(blockId, facultyId, excludedId); 
	}
}
