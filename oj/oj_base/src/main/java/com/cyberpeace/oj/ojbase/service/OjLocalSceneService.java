package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;

public interface OjLocalSceneService extends IService<OjLocalScene> {

    /**
     * 查询ojLocalScene数据
     * @param businessId 资源编号 课程编号 课时编号 习题编号
     * @param shared 是否共享 1=私有 2=共享
     * @param authContext 前后台权限数据
     * @return
     */
    OjLocalScene selectOjLocalScene(String businessId, Integer shared, AuthContext authContext);

}
