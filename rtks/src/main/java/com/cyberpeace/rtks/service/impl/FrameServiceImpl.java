package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.service.FrameService;

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
		Level frame = getById(frameId);
		if (frame == null) {
			throw new ResourceNotFoundException("找不到" + frameId + "对应的框架");
		}

		ensureIsFrame(frame);

		return frame;
	}

	/**
	 * 给定level对象，确保其为框架，否则报错
	 * */
	private void ensureIsFrame(Level level) {
		if (StringUtils.isNotBlank(level.getParent())) {
			throw new InvalidParamException(level.getResourceId() + "不是框架而是层级");
		}
	}

	@Override
	public List<String> checkFrameIds(List<String> ids) {
		List<Level> frames = levelMapper.selectBatchIds(ids);

		List<String> ret = new ArrayList<>();
		for (Level frame : frames) {
			ensureIsFrame(frame);
			ret.add(frame.getResourceId());
		}

		return ret;
	}
}