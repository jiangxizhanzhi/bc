package com.cyberpeace.situation.dao.mapper;

import com.cyberpeace.situation.dao.entity.SubRange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午4:37
 */
@Mapper
public interface SubRangeMapper {

    List<SubRange> getSubRanges();

    SubRange getById(@Param("subRangeId") String subRangeId);
}
