package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;

@Mapper
@Repository
public interface PracticeCategoryMapper extends BaseMapper<PracticeCategory> {

	/**
	 * 分页获取一级节点
	 * @param page
	 * @param pattern
	 * @param practiceType
	 * @return
	 */
	IPage<PracticeCategory> pageByTypeAndName(Page page, @Param("pattern") String pattern,
			@Param("practiceType") Integer practiceType);

	/**
	 * 搜索子分类
	 * @param parentCode
	 * @return
	 */
	List<PracticeCategory> selectByCode(@Param("code") String parentCode);

	/**
	 * 查询列表 名称和类型
	 * @param practiceType
	 * @param pattern
	 * @return
	 */
	List<PracticeCategory> listByTypeAndName(@Param("practiceType") Integer practiceType,
			@Param("pattern") String pattern);

	/**
	 * 根据分类名称查询分类编号
	 * @param name
	 * @param practiceType
	 * @return
	 */
	String selectByName(@Param("practiceType") Integer practiceType, @Param("name") String name);
}
