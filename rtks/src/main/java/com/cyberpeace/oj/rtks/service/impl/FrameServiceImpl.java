package com.cyberpeace.oj.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.InvalidParamException;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.oj.rtks.service.FrameService;

@Service
public class FrameServiceImpl extends ServiceImpl<LevelMapper, Level> implements FrameService {

	@Autowired
	LevelMapper levelMapper;

	@Override
	public IPage<Level> listEntity(Page<Level> page, String keyword) {
		return levelMapper.listFrame(page, keyword);
	}

	@Override
	public Level getOr404(String frameId) {
		Level level = getById(frameId);
		if (level == null) {
			throw new ResourceNotFoundException("找不到" + frameId + "对应的框架");
		}

		if (level.getParent() != null) {
			// level有父级，不是Frame
			throw new InvalidParamException(frameId + "不是框架id");
		}

		return level;
	}

}