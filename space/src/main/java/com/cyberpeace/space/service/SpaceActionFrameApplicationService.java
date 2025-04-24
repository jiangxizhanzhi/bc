
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.service.context.frame.ApplicationUser;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationReq;

import java.util.List;
import java.util.Map;

public interface SpaceActionFrameApplicationService extends IService<SpaceActionFrameApplication>{
    void create(SpaceActionFrameApplicationReq req);

    List<ApplicationUser> getUsers(String rvbId, Integer userGroup);

    Map<String, String> getUserMap(String rvbId, Integer userGroup);

    void deleteByIds(List<String> ids);
}
    