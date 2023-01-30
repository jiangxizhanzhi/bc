package com.cyberpeace.situation.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.situation.dao.entity.EventType;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午5:41
 */
@Mapper
public interface EventTypeMapper {

	List<EventType> getEventTypeContextList(@Param("isRootNode") Boolean isRootNode);

	List<EventType> selectBatchByName(@Param("types") Collection<String> types);
}
