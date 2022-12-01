package com.cyberpeace.situation.dao.mapper;

import com.cyberpeace.situation.service.context.EventTypeContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午5:41
 */
@Mapper
public interface EventTypeContextMapper {

    List<EventTypeContext> getEventTypeContextList(@Param("isRootNode") Boolean isRootNode);

}
