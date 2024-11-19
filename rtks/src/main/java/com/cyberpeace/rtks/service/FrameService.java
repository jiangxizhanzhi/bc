package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Level;

/**
 * 框架Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface FrameService extends IService<Level> {

	/**
	 * 给定一个id的数组，确保这些id对应框架(非层级)都存在，返回去重后的框架id数组
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> checkFrameIds(List<String> ids);

	/**
	 * 获取OR404
	 *
	 * @param resourceId 资源id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
	Level getOr404(String resourceId);

	/**
	 * 列表实体
	 *
	 * @param page    分页
	 * @param keyword 关键词
	 * @return {@code IPage<Level> }
	 * @throws
	 * @see Page<Level>
	 * @see String
	 * @see IPage<Level>
	 */
	IPage<Level> listEntity(Page<Level> page, String keyword);

	/**
	 * 获取树
	 *
	 * @param frameId 框架id
	 * @return {@code LevelTreeOutput }
	 * @throws
	 * @see String
	 * @see LevelTreeOutput
	 */
	LevelTreeOutput getTree(String frameId);

}
