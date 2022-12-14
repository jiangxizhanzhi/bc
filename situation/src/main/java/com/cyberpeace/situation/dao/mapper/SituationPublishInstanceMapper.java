package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.res.SituationPublishInstanceListRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SituationPublishInstanceMapper extends BaseMapper<SituationPublishInstance> {

    IPage<SituationPublishInstancePageRes> getSituationPublishInstancePage(@Param("page") Page page,
                                                                           @Param("situationPublishInstanceFilterReq") SituationPublishInstanceFilterReq situationPublishInstanceFilterReq);
}
