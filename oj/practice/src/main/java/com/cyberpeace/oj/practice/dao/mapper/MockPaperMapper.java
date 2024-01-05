package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.MockPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MockPaperMapper extends BaseMapper<MockPaper> {
    List<MockPaper> selectByCreator(@Param("userId") String userId, @Param("actual") boolean actual);

    MockPaper selectByPresetIdAndUserId(@Param("presetId") String presetId, @Param("userId") String userId);
}
