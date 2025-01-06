package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.service.context.index.IndexStrategy;
import com.cyberpeace.space.service.context.strategy.*;

import java.util.List;

/**
 * 空间策略Service
 *
 * @author SN
 * @date 2024/11/13
 */
public interface SpaceStrategyService extends IService<SpaceStrategy> {
    /**
     * 获取框架
     *
     * @return {@code SpaceStrategyFrameBo }
     */
    SpaceStrategyFrameBo getFrame();

    /**
     * 新增框架
     *
     * @param req 请求体
     */
    void addFrame(SpaceStrategyFrameAddReq req);

    /**
     * 修改
     *
     * @param req 请求体
     */
    void modify(SpaceStrategyFrameModifyReq req);

    /**
     * 删除框架
     *
     * @param resourceId 资源id
     */
    void deleteFrame(String resourceId);

    /**
     * 列表策略由框架
     *
     * @param frameId 框架id
     * @param name    名称
     * @return {@code List<SpaceStrategySimple> }
     */
    List<SpaceStrategySimple> listStrategiesByFrame(String frameId, String name);

    /**
     * 新增策略
     *
     * @param req 请求体
     */
    void addStrategy(SpaceStrategyAddReq req);

    /**
     * 更新策略
     *
     * @param req 请求体
     */
    void updateStrategy(SpaceStrategyModifyReq req);

    /**
     * 删除策略
     *
     * @param resourceId 资源id
     */
    void deleteStrategy(String resourceId);

    /**
     * 获取详情
     *
     * @param resourceId 资源id
     * @return {@code SpaceStrategyBo }
     */
    SpaceStrategyBo getDetail(String resourceId);

    /**
     * 获取框架路线由id
     *
     * @param frameId 框架id
     * @return {@code String }
     */
    String getFrameRouteById(String frameId);

    /**
     * 获取统计学
     *
     * @return {@code IndexStrategy }
     */
    IndexStrategy getStatistics();

    ListRsp<SpaceStrategySimple> pageStrategiesByFrame(PageReqBase req, String frameId, String name);
}
