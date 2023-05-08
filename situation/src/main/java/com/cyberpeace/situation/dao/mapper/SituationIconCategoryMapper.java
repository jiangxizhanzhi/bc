package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;

/**
 * 图标类别映射器
 *
 * @Author xuxz
 * @Date 15/12/2022 下午12:00
 */
@Mapper
public interface SituationIconCategoryMapper extends BaseMapper<SituationIconCategory> {

	/**
	 * 获取全部图标
	 * @return
	 */
	List<SituationIconCategory> selectAll();

	/**
	 * 名称查询
	 * @param name
	 * @return
	 */
	String selectByName(String name);
}
