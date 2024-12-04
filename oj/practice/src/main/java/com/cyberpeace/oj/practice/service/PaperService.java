package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.bo.PaperContext;
import com.cyberpeace.oj.practice.service.bo.LibPracticeListBo;
import com.cyberpeace.oj.practice.service.bo.LibPracticeQueryBo;
import com.cyberpeace.oj.practice.service.bo.RandomPracticeQuery;

import java.util.List;

/**
 * 纸Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface PaperService extends IService<Paper> {

	/**
	 * 试卷创建
	 *
	 * @param paperContext 修改传入子类
	 * @return
	 * @throws
	 * @see PaperContext
	 * @see Paper
	 */
	Paper create(PaperContext paperContext);

	/**
	 * 试卷修改
	 *
	 * @param paperContext
	 * @return
	 * @throws
	 * @see PaperContext
	 * @see Paper
	 */
	Paper update(PaperContext paperContext);

	/**
	 * 题库数据查询
	 *
	 * @param page
	 * @param queryBo
	 * @param authContext
	 * @return
	 * @throws
	 * @see Page
	 * @see LibPracticeQueryBo
	 * @see AuthContext
	 * @see IPage<LibPracticeListBo>
	 */
	IPage<LibPracticeListBo> selectLibPractice(Page page, LibPracticeQueryBo queryBo,
			AuthContext authContext);

	/**
	 * 获取随机数量习题
	 *
	 * @param practiceQuery
	 * @param score
	 * @param authContext   权限数据
	 * @return
	 * @throws
	 * @see RandomPracticeQuery
	 * @see Double
	 * @see AuthContext
	 * @see List<LibPracticeListBo>
	 */
	List<LibPracticeListBo> selectRandomPractice(RandomPracticeQuery practiceQuery, Double score,
			AuthContext authContext);

	/**
	 * 查询试卷下习题数量
	 *
	 * @param paperId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer practiceCountByPaper(String paperId);
}
