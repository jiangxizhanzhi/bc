package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskAnswer;

public interface CrTaskAnswerService extends IService<CrTaskAnswer> {

	public void addTaskAnswer(CrTaskAnswer crTaskAnswer);

	public CrTaskAnswer getById(String taskAnswerId);
}
