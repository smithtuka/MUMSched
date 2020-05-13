package mum.swe.mumsched.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.model.Entry;

/**
 * @author Elsabeth

 * @date May 13, 2020

 */
@Repository
public interface BlockRepository extends CrudRepository<Block, Long> {
	@Query("SELECT e FROM Block e")
	public Iterable<Block> fillAllWithSort(Sort sort);
	
	@Query("SELECT CASE WHEN count(e)> 0 THEN true ELSE false END FROM Block e WHERE e.schedule.id = :scheduleId AND e.month=:month AND e.id <> :excludedId")
	public boolean hasExistsBlock(@Param("scheduleId") Long scheduleId, @Param("month") MonthEnum month, @Param("excludedId") Long excludedId);
	
//	@Query("SELECT e FROM Block e WHERE e.schedule.id = :scheduleId")
//	public Iterable<Block> findAllByScheduleId(@Param("scheduleId") Long scheduleId);
}
