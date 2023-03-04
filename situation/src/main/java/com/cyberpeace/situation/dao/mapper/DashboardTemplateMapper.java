package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.DashboardTemplatePageReq;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午4:22
 */
@Mapper
public interface DashboardTemplateMapper extends BaseMapper<DashboardTemplate> {

    IPage<DashboardTemplatePageRes> selectPage(@Param("page") Page page,
                                               @Param("dashboardTemplatePageReq") DashboardTemplatePageReq dashboardTemplatePageReq);

    String selectByNameString(@Param("name")String name);

    String selectByType(@Param("type")String type);

    String selectByLevel(@Param("level")Integer level);
}
