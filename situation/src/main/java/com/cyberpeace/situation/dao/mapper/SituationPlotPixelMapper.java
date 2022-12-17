package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.req.SituationPlotPixelPageReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author xuxz
 * @Date 17/12/2022 下午5:11
 */
@Mapper
public interface SituationPlotPixelMapper extends BaseMapper<SituationPlotPixel> {
    /**
     * 分页查询
     * @param page
     * @param situationPlotPixelPageReq
     * @return
     */
    IPage<SituationPlotPixel> selectPage(@Param("page") Page page,
                                         @Param("situationPlotPixelPageReq") SituationPlotPixelPageReq situationPlotPixelPageReq);
}
