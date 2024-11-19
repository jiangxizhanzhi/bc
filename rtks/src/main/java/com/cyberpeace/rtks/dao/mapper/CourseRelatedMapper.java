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
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface CourseRelatedMapper extends BaseMapper<CourseRelated> {

	/**
	 * 删除由相关id
	 *
	 * @param ids  id列表
	 * @param type 类型
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	void deleteByRelatedId(@Param("ids") List<String> ids, Integer type);
}
