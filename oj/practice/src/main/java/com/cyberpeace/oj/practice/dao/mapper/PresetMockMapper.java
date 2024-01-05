package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PresetMock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PresetMockMapper extends BaseMapper<PresetMock> {
    List<PresetMock> selectAll(@Param("actual") boolean actual);

}
