package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceStrategyFrame;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 空间策略框架Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceStrategyFrameMapper extends BaseMapper<SpaceStrategyFrame> {
    List<SpaceStrategyFrame> getAll();
}
