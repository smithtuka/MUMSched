package mum.swe.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.model.Schedule;
import mum.swe.mumsched.repository.ScheduleViewRepository;
import mum.swe.mumsched.service.ScheduleViewService;

/**
 * @author Rachel

 * @date May 6, 2020

 */
@Service
public class ScheduleViewServiceImpl implements ScheduleViewService{
	@Autowired
	ScheduleViewRepository repo;
	
	@Override
	public Schedule findOneByEntryId(Long entryId) {
		return repo.findOneByEntryId(entryId);
	}

	@Override
	public List<Schedule> findAll() {
		return repo.findAll();
	}
}
