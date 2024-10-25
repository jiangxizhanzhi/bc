package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.service.SpaceActionFrameService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceActionFrameServiceImpl extends ServiceImpl<SpaceActionFrameMapper, SpaceActionFrame> implements SpaceActionFrameService{

	@Override
	public Integer countByType(Integer code) {
		LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrame::getType, code);
		return this.count(wrapper);
	}
}