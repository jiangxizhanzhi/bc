package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.ComponentLayer;

/**
 * 组件图层映射器
 *
 * @Author xuxz
 * @Date 30/12/2022 上午9:36
 */

@Mapper
public interface SituationComponentLayerMapper extends BaseMapper<ComponentLayer> {

	/**
	 * 批量插入
	 *
	 * @param componentLayerList 组件图层列表
	 */
	void batchInsert(@Param("componentLayerList") List<ComponentLayer> componentLayerList);

	/**
	 * 通过模板id选择
	 *
	 * @param templateId 模板id
	 * @return {@code List<ComponentLayer>}
	 */
	List<ComponentLayer> selectByTemplateId(@Param("templateId") String templateId);
}
