package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.GisPageReq;
import com.cyberpeace.situation.controller.res.GisPageRes;
import com.cyberpeace.situation.dao.entity.Gis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:12
 */
@Mapper
public interface SituationGisMapper extends BaseMapper<Gis> {

    IPage<GisPageRes> selectPage(@Param("page") Page page,
                                 @Param("GisPageReq") GisPageReq GisPageReq);
}
