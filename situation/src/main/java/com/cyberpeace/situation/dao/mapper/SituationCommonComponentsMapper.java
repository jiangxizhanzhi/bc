package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:12
 */
@Mapper
public interface SituationCommonComponentsMapper extends BaseMapper<SituationCommonComponents> {

    IPage<SituationCommonComponentsPageRes> selectPage(@Param("page") Page page,
                                 @Param("situationCommonComponentsPageReq") SituationCommonComponentsPageReq situationCommonComponentsPageReq);
}
