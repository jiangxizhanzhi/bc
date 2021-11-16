package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.bo.AddOrUpdatePaperContext;

public interface PaperService extends IService<Paper> {

	/**
	 * 试卷创建
	 * @param paperContext 修改传入子类
	 * @return
	 */
	Paper createOrUpdate(AddOrUpdatePaperContext paperContext);

}
