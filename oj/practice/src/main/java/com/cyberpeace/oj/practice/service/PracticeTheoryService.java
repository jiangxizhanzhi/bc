package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

public interface PracticeTheoryService extends IService<PracticeTheory> {

	/**
	 * 分页查询理论基础练习题
	 * @param page
	 * @param listQuery
	 * @param authContext
	 * @return
	 */
	IPage<PracticeTheoryListBo> pageByQuery(Page page, TheoryListQueryBo listQuery,
			AuthContext authContext);

	/**
	 * 用户根据编号查询题目详细信息
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	WebTheoryBo selectById(String resourceId, String userId);
}
