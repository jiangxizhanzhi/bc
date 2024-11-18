package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用状态映射
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface SystemUseStatusMapper extends BaseMapper<SystemUseStatus> {

    /**
     * 查询列表
     *
     * @return {@code List<SystemUseStatus> }
     * @throws
     * @see List<SystemUseStatus>
     */
    List<SystemUseStatus> selectList();

    /**
     * 删除过期数据
     *
     * @throws
     */
    void deleteOverdueData();
}
