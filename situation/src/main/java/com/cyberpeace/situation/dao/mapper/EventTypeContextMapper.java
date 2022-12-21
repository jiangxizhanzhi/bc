package com.cyberpeace.situation.dao.mapper;

import com.cyberpeace.situation.dao.entity.EventTypeContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午5:41
 */
@Mapper
public interface EventTypeContextMapper {

    List<EventTypeContext> getEventTypeContextList(@Param("isRootNode") Boolean isRootNode);

    List<EventTypeContext> selectBatchByName(@Param("types") Collection<String> types);
}
