package com.cyberpeace.oj.rtks.service.impl;

import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.oj.rtks.service.LevelService;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {

	@Autowired
	private LevelMapper levelMapper;

	@Override
	public void validateLevelId(String frameId) {
		if (frameId == null) {
			return;
		}
		Level level = levelMapper.selectById(frameId);
		if (level == null) {
			throw new ResourceNotFoundException("Frame");
		}
	}
}