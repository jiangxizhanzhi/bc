
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.service.context.frame.ApplicationUser;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationReq;

import java.util.List;
import java.util.Map;

/**
 * 空间行动框架行为依赖Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceActionFrameApplicationService extends IService<SpaceActionFrameApplication>{
    void create(SpaceActionFrameApplicationReq req);

    List<ApplicationUser> getUsers(String rvbId, Integer userGroup);

    Map<String, String> getUserMap(String rvbId, Integer userGroup);

    void deleteByIds(List<String> ids);
}
    