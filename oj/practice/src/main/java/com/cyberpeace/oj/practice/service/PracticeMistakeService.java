package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;

import java.util.List;

public interface PracticeMistakeService extends IService<PracticeMistake> {

	Integer getTheoryMistakeCount(String userId, Integer practiceType);

	void addIncorrect(String userId, String practiceId, Integer practiceType);

	void addCorrect(String userId, String practiceId, Integer practiceType);

	List<PersonalMistakeRes> getPracticeMistake();
}
