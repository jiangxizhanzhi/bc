package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.context.SetListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.service.externalBo.PracticeSetSimple;

@Mapper
public interface PracticeSetMapper extends BaseMapper<PracticeSet> {

	/**
	 * 查询所有习题库 #外部接口使用
	 * @return
	 */
	List<PracticeSetSimple> getAll();

	/**
	 * 根据权限获取可访问的资源数据
	 * @param authContext
	 * @return
	 */
	List<String> selectByAuthContext(@Param("authContext") AuthContext authContext);

	/**
	 * 根据类型和名称分页查询
	 * @param page
	 * @param listQuery
	 * @param isPublic
	 * @return
	 */
	IPage<PracticeSet> pageByTypeAndName(Page page, @Param("listQuery") SetListQuery listQuery,
			@Param("isPublic") Boolean isPublic);

	List<PracticeSet> selectByType(@Param("practiceType") Integer practiceType,
			@Param("isPublic") Boolean isPublic);
}
