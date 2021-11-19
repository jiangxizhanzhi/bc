package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.bo.AddOrUpdatePaperContext;
import com.cyberpeace.oj.practice.service.bo.LibPracticeListBo;
import com.cyberpeace.oj.practice.service.bo.LibPracticeQueryBo;

public interface PaperService extends IService<Paper> {

	/**
	 * 试卷创建
	 * @param paperContext 修改传入子类
	 * @return
	 */
	Paper create(AddOrUpdatePaperContext paperContext);

	/**
	 * 试卷修改
	 * @param paperContext
	 * @return
	 */
	Paper update(AddOrUpdatePaperContext paperContext);

	/**
	 * 题库数据查询
	 * @param page
	 * @param queryBo
	 * @param authContext
	 * @return
	 */
    IPage<LibPracticeListBo> selectLibPractice(Page page, LibPracticeQueryBo queryBo, AuthContext authContext);
}
