package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseReq;

import java.util.List;

/**
 * 空间行动框架下发Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceActionFrameReleaseService extends IService<SpaceActionFrameRelease> {
    /**
     * 创建
     *
     * @param req 请求体
     * @throws
     * @see SpaceActionFrameReleaseReq
     */
    void create(SpaceActionFrameReleaseReq req);

    /**
     * 获取行为依赖Bo
     *
     * @param resourceId 资源id
     * @return 空间行动框架行为依赖Bo
     * @throws
     * @see String
     * @see SpaceActionFrameApplicationBo
     */
    SpaceActionFrameApplicationBo getApplicationBo(String resourceId);

    /**
     * 获取策略
     *
     * @param applicationId 行为依赖id
     * @return 列表<空间行动框架次序博>
     * @throws
     * @see String
     * @see List<SpaceActionFrameOrderBo>
     */
    List<SpaceActionFrameOrderBo> getTactics(String applicationId);
}
