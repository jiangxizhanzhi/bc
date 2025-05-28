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
    void create(SpaceActionFrameReleaseReq req);

    SpaceActionFrameApplicationBo getApplicationBo(String resourceId);

    List<SpaceActionFrameOrderBo> getTactics(String applicationId);
}
