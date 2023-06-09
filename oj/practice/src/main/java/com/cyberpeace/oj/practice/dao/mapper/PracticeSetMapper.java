package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.context.SetListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PracticeSetMapper extends BaseMapper<PracticeSet> {

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

	/**
	 * 根据类型和名称分页查询
	 * @param page
	 * @param listQuery
	 * @param isPublic
	 * @return
	 */
	IPage<PracticeSet> pageByNameAndType(Page page, @Param("listQuery") SetListQuery listQuery,
										 @Param("isPublic") Boolean isPublic);

	/**
	 * 根据习题集类型查询习题集
	 * @param practiceType
	 * @param isPublic
	 * @return
	 */
	List<PracticeSet> selectByType(@Param("practiceType") Integer practiceType,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 根据习题集名称查询
	 * @param name
	 * @return
	 */
	String selectByName(@Param("name") String name);

	/**
	 * 根据习题集名称查询
	 * @param name
	 * @return
	 */
	List<PracticeSet> selectByNames(@Param("names") List<String> name);
	
	List<String> selectByAuthAndPublic(@Param("authContext") AuthContext authContext,
			@Param("isPublic") Boolean isPublic);

	List<PracticeSet> selectPracticeSetByUserId(String userId);

	List<PracticeSet> selectPracticeSetByOrgId(String orgId);

	List<PracticeSet> selectByIdsAndType(@Param("setIds") List<String> setIds,@Param("setType") Integer type);

	List<PracticeSet> selectInResourceIds(@Param("resourceIds") List<String> resourceIds);
}
