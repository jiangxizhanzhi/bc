package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.CourseRelated;

/**
 * 课程相关Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface CourseRelatedMapper extends BaseMapper<CourseRelated> {

	/**
	 * 删除由相关id
	 *
	 * @param ids  id列表
	 * @param type 类型
	 */
	void deleteByRelatedId(@Param("ids") List<String> ids, Integer type);
}
