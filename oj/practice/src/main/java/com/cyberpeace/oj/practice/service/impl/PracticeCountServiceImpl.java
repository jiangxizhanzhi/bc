package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.practice.dao.entity.PracticeCount;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCountMapper;
import com.cyberpeace.oj.practice.service.PracticeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeCountServiceImpl extends ServiceImpl<PracticeCountMapper, PracticeCount> implements PracticeCountService {

	@Autowired
	private PracticeCountMapper practiceCountMapper;

	@Override
	public void add(String userId) {
		PracticeCount practiceCount = practiceCountMapper.selectByUserId(userId);
		if (practiceCount != null) {
			practiceCount.setCount(practiceCount.getCount() + 1);
			practiceCountMapper.updateById(practiceCount);
		} else {
			practiceCount = new PracticeCount();
			practiceCount.setUserId(userId);
			practiceCount.setCount(1);
			practiceCountMapper.insert(practiceCount);
		}
	}

	@Override
	public Integer getAllPracticeCount() {
		return practiceCountMapper.getAllPracticeCount();
	}

	@Override
	public Integer getAllPeopleCount() {
		return practiceCountMapper.getAllPeopleCount();
	}
}
