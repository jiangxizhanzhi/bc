package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.ColorThemePageReq;
import com.cyberpeace.situation.dao.entity.ColorTheme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午9:08
 */
@Mapper
public interface SituationColorThemeMapper extends BaseMapper<ColorTheme> {

    IPage<ColorTheme> selectPage(@Param("page") Page page,
                                 @Param("colorThemePageReq") ColorThemePageReq colorThemePageReq);
}
