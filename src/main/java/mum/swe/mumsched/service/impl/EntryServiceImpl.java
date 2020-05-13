package mum.swe.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.model.Entry;
import mum.swe.mumsched.repository.EntryRepository;
import mum.swe.mumsched.service.EntryService;

/**
 * @author Elsabeth

 * @date May 10, 2020
 */
@Service
public class EntryServiceImpl implements EntryService {
	@Autowired
	EntryRepository entryRepo;
	
	@Override
	public List<Entry> getList(){
		return entryRepo.fillAllWithSort(new Sort(Direction.DESC, "entryDate"));
	}
	
	@Override
	public boolean hasExistsEntryName(String name, long excludedId) {
		return entryRepo.hasExistsEntryName(name, excludedId);
	}
	
	@Override
	public Entry findEntryById(Long id) {
		return entryRepo.findOne(id);
	}
	
	@Override
	public boolean hasStudentRef(Entry entry) {
		return entry.getStudentList().size() > 0;
	}
	
	@Override
	public boolean hasScheduleRef(Entry entry) {
		return entry.getSchedule() != null;
	}
	
	@Override
	public Entry save(Entry entry) {
		return entryRepo.save(entry);
	}
	
	@Override
	public void delete(Entry entry) {
		entryRepo.delete(entry);
	}
}
