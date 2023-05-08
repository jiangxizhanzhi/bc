package com.cyberpeace.situation.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.EventType;

/**
 * 事件类型映射器
 *
 * @Author xuxz
 * @Date 30/11/2022 下午5:41
 */
@Mapper
public interface EventTypeMapper extends BaseMapper<EventType> {

	/**
	 * 获取事件类型列表
	 *
	 * @param isRootNode 是否为一级类型
	 * @return {@code List<EventType>}
	 */
	List<EventType> getEventTypeContextList(@Param("isRootNode") Boolean isRootNode);

}
