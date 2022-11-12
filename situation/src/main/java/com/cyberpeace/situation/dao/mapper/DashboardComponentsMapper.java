package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午4:30
 */
@Mapper
public interface DashboardComponentsMapper extends BaseMapper<DashboardComponents> {

    void batchInsert(@Param("dashboardComponentsList") List<DashboardComponents> dashboardComponentsList);
}
