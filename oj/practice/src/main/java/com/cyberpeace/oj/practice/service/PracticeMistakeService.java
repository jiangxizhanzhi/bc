package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;

public interface PracticeMistakeService extends IService<PracticeMistake> {

	Integer getTheoryMistakeCount(String userId, Integer practiceType);

	Boolean addIncorrect(String userId, String practiceId, Integer practiceType);

	Boolean addCorrect(String userId, String practiceId, Integer practiceType);
}
