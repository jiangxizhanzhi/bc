package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用状态Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
public interface SystemUseStatusMapper extends BaseMapper<SystemUseStatus> {

    /**
     * 查询列表
     *
     * @return {@code List<SystemUseStatus> }
     */
    List<SystemUseStatus> selectList();

    /**
     * 删除过期数据
     */
    void deleteOverdueData();
}
