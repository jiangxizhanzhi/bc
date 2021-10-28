package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.bo.AddOrUpdatePaperContext;
import com.cyberpeace.oj.practice.service.bo.TestPaperInfo;

public interface PaperService extends IService<Paper> {

	TestPaperInfo getSingleInfo(String resourceId);

	/**
	 * 试卷创建
	 * @param paperContext 修改传入子类
	 * @return
	 */
	Paper createOrUpdate(AddOrUpdatePaperContext paperContext);

	/**
	 * 试卷列表查询
	 * @param page
	 * @param pattern
	 * @return
	 */
	ListRsp<Paper> getList(Page page, String pattern);
}
