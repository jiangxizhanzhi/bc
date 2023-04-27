package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;
import com.cyberpeace.oj.practice.dao.mapper.PracticeMistakeMapper;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeMistakeServiceImpl extends ServiceImpl<PracticeMistakeMapper, PracticeMistake> implements PracticeMistakeService {

	@Autowired
	private PracticeMistakeMapper practiceMistakeMapper;

	@Override
	public Integer getTheoryMistakeCount(String userId, Integer practiceType) {
		return practiceMistakeMapper.countByUserId(userId, practiceType);
	}
}
