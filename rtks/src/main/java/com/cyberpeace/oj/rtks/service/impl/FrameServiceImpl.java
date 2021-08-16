package com.cyberpeace.oj.rtks.service.impl;

import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.rtks.dao.entity.Frame;
import com.cyberpeace.oj.rtks.dao.mapper.FrameMapper;
import com.cyberpeace.oj.rtks.service.FrameService;

@Service
public class FrameServiceImpl extends ServiceImpl<FrameMapper, Frame> implements FrameService {

	@Autowired
	private FrameMapper frameMapper;

	@Override
	public void validateFrameId(String frameId) {
		if (frameId == null) {
			return;
		}
		Frame frame = frameMapper.selectById(frameId);
		if (frame == null) {
			throw new ResourceNotFoundException("Frame");
		}
	}
}