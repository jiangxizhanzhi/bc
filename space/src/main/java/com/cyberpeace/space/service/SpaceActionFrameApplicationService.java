
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
    /**
     * 创建
     *
     * @param req 请求体
     * @throws
     * @see SpaceActionFrameApplicationReq
     */
    void create(SpaceActionFrameApplicationReq req);

    /**
     * 获取用户
     *
     * @param rvbId     RVBid
     * @param userGroup 用户组
     * @return 列表<行为依赖用户>
     * @throws
     * @see String
     * @see Integer
     * @see List<ApplicationUser>
     */
    List<ApplicationUser> getUsers(String rvbId, Integer userGroup);

    /**
     * 获取用户映射
     *
     * @param rvbId     RVBid
     * @param userGroup 用户组
     * @return map<string 、 string>
     * @throws
     * @see String
     * @see Integer
     * @see Map<String, String>
     */
    Map<String, String> getUserMap(String rvbId, Integer userGroup);

    /**
     * 删除由id列表
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteByIds(List<String> ids);
}
    