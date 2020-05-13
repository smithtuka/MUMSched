package mum.swe.mumsched.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.model.Section;
import mum.swe.mumsched.repository.BlockRepository;
import mum.swe.mumsched.service.BlockService;

/**
 * @author Elsabeth

 * @date May 13, 2020

 */
@Service
public class BlockServiceImpl implements BlockService {
	@Autowired
	BlockRepository blockRepo;
	
	@Override
	public Iterable<Block> getList(){
		return blockRepo.fillAllWithSort(new Sort(Direction.DESC, "month"));
	}
	
	@Override
	public Block findBlockById(Long id) {
		return blockRepo.findOne(id);
	}
	
	@Override
	public boolean hasExistsBlock(long scheduleId, MonthEnum month, long excludedId) {
		return blockRepo.hasExistsBlock(scheduleId, month, excludedId);
	}
	
	@Override
	public boolean hasSectionRef(Block block) {
		return block.getSectionList().size() > 0;
	}
	
	@Override
	public Block save(Block Block) {
		return blockRepo.save(Block);
	}
	@Override
	public List<Block> saveAll(List<Block> Blocks) {
		return (List<Block>)blockRepo.save(Blocks);
	}
	
	@Override
	public void delete(Block Block) {
		blockRepo.delete(Block);
	}

	@Override
	public Block combine2Blocks(Block b1, Block b2) {
		Block block = b1;
		Set<Section> sections1 = new LinkedHashSet<Section>(b1.getSectionList());
		Set<Section> sections2 = new LinkedHashSet<Section>(b2.getSectionList());
		sections1.addAll(sections2);
		block.setSectionList(new LinkedHashSet<Section>(sections1));
		//schedule.setBlockList(new LinkedHashSet<Block>(blocks));

		return block;
	}

	public Iterable<Block> findAll() {
		return blockRepo.findAll();
	}
}
