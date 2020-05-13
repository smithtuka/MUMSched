package mum.swe.mumsched.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mum.swe.mumsched.model.Schedule;

/**
 * @author Rachel

 * @date May 6, 2020

 */

@Repository
public interface ScheduleViewRepository extends JpaRepository<Schedule,Long>{
	@Query("select s from Schedule s where s.entry.id = :entryId")
	Schedule findOneByEntryId(@Param("entryId") Long entryId);
}
