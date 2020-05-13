package mum.swe.mumsched.service;

import java.util.List;

import mum.swe.mumsched.model.Schedule;

public interface ScheduleViewService {
	Schedule findOneByEntryId(Long entryId);
	List<Schedule> findAll();
}
