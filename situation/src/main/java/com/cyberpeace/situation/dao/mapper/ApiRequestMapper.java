package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.situation.controller.req.ApiRequestPageReq;
import com.cyberpeace.situation.controller.res.ApiRequestRes;
import com.cyberpeace.situation.dao.entity.ApiRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午9:57
 */
@Mapper
public interface ApiRequestMapper extends BaseMapper<ApiRequest> {
    /**
     * 分页查询
     * @param page
     * @param authContext
     * @param apiRequestPageReq
     * @return
     */
    IPage<ApiRequestRes> selectPage(@Param("page") Page page,
                                    @Param("authContext")AuthContext authContext,
                                    @Param("apiRequestPageReq") ApiRequestPageReq apiRequestPageReq);

    /**
     * 根据用户获取列表
     * @param authContext
     * @return
     */
    List<String> selectByAuthContext(@Param("authContext") AuthContext authContext);

    ApiRequest selectById(@Param("resourceId")String resourceId);

}
