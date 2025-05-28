package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceCallCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间调用计数Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/12
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceCallCountMapper extends BaseMapper<SpaceCallCount> {
}
