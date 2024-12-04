package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实践类别Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface PracticeCategoryMapper extends BaseMapper<PracticeCategory> {

	/**
	 * 分页获取一级节点
	 *
	 * @param page
	 * @param pattern
	 * @param practiceType
	 * @return
	 * @throws
	 * @see Page
	 * @see String
	 * @see Integer
	 * @see IPage<PracticeCategory>
	 */
	IPage<PracticeCategory> pageByTypeAndName(Page page, @Param("pattern") String pattern,
			@Param("practiceType") Integer practiceType);

	/**
	 * 搜索子分类
	 *
	 * @param parentCode
	 * @return
	 * @throws
	 * @see String
	 * @see List<PracticeCategory>
	 */
	List<PracticeCategory> selectByCode(@Param("code") String parentCode);

	/**
	 * 查询列表 名称和类型
	 *
	 * @param practiceType
	 * @param pattern
	 * @return
	 * @throws
	 * @see Integer
	 * @see String
	 * @see List<PracticeCategory>
	 */
	List<PracticeCategory> listByTypeAndName(@Param("practiceType") Integer practiceType,
			@Param("pattern") String pattern);

	/**
	 * 根据分类名称查询分类编号
	 *
	 * @param practiceType
	 * @param name
	 * @return
	 * @throws
	 * @see Integer
	 * @see String
	 * @see String
	 */
	String selectByName(@Param("practiceType") Integer practiceType, @Param("name") String name);
}
