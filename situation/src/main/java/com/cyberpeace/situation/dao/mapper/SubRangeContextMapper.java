package com.cyberpeace.situation.dao.mapper;

import com.cyberpeace.situation.service.context.SubRangeContext;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:37
 */
@Mapper
public interface SubRangeContextMapper {

    List<SubRangeContext> getSubRanges();
}
