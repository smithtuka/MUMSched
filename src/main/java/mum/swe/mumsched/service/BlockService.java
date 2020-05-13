package mum.swe.mumsched.service;

import java.util.List;
import java.util.Set;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.model.Block;

/**
 * @author Elsabeth

 * @date May 13, 2020

 */
public interface BlockService {

	Iterable<Block> getList();

	Block findBlockById(Long id);

	boolean hasExistsBlock(long scheduleId, MonthEnum month, long excludedId);

	Block save(Block Block);

	void delete(Block Block);

	boolean hasSectionRef(Block block);
	
	Block combine2Blocks(Block b1, Block b2);

	List<Block> saveAll(List<Block> Blocks);

	Iterable<Block> findAll();
}
