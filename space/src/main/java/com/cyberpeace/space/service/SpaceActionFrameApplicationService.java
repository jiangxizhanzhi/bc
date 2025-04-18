
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationReq;

public interface SpaceActionFrameApplicationService extends IService<SpaceActionFrameApplication>{
    void create(SpaceActionFrameApplicationReq req);
}
    