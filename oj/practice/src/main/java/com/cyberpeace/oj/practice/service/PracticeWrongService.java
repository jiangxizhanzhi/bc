package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.res.PracticeWrongRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeWrong;

import java.util.List;

public interface PracticeWrongService extends IService<PracticeWrong> {

	List<PracticeWrongRes> getAllPracticeWrong();
}
