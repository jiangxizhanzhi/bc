package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeCount;

public interface PracticeCountService extends IService<PracticeCount> {

	void add(String userId);

	Integer getAllPracticeCount();

	Integer getAllPeopleCount();
}
