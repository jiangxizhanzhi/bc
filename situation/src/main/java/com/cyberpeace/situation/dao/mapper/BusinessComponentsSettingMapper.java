package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import com.cyberpeace.situation.dao.entity.BusinessComponentsSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 大屏组件映射器
 *
 * @Author xuxz
 * @Date 11/11/2022 下午4:30
 */
@Mapper
public interface BusinessComponentsSettingMapper extends BaseMapper<BusinessComponentsSetting> {

	/**
	 * 批量插入
	 *
	 * @param dashboardComponentsList 大屏组件列表
	 */
	void batchInsert(
			@Param("dashboardComponentsList") List<BusinessComponentsSetting> dashboardComponentsList);

}
