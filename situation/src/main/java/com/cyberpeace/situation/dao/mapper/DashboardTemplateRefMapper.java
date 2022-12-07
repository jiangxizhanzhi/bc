package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午4:22
 */
@Mapper
public interface DashboardTemplateRefMapper extends BaseMapper<DashboardTemplateRef> {

    /**
     * 获取未使用的模板数据
     * @return
     */
    List<String> selectAllId();
}
