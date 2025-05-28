package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameRecBo;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;

import java.util.List;
import java.util.Map;

/**
 * 空间行动框架Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/05
 * @see IService
 * @since 1.0.0
 */
public interface SpaceActionFrameService extends IService<SpaceActionFrame> {

    /**
     * 计数由类型
     *
     * @param code 代号
     * @return {@code Integer }
     */
    Integer countByType(Integer code);

    /**
     * 获取框架水平
     *
     * @param resourceId 资源id
     * @return {@code SpaceActionFrameLevel }
     */
    SpaceFrameLevel getFrameLevels(String resourceId);

    /**
     * 保存框架
     *
     * @param req 请求体
     */
    boolean saveFrame(SpaceFrameLevel req);

    /**
     * 保存框架
     *
     * @param req 请求体
     * @throws
     * @see ActionFrameArrangeBo
     */
    void saveFrame(ActionFrameArrangeBo req);

    /**
     * 记录使用情况
     *
     * @param type 类型
     * @throws
     * @see String
     */
    void recordUsage(String type);

    /**
     * 获取框架
     *
     * @param resourceId 资源id
     * @return 行动框架安排Bo
     * @throws
     * @see String
     * @see ActionFrameArrangeBo
     */
    ActionFrameArrangeBo getFrame(String resourceId);

    /**
     * 搜索框架
     *
     * @param req 请求体
     * @return 列表<空间行动框架博>
     * @throws
     * @see String
     * @see List<SpaceActionFrameBo>
     */
    List<SpaceActionFrameBo> searchFrame(String req);

    /**
     * 获取战法名称映射
     *
     * @param bo Bo
     * @return map<string 、 string>
     * @throws
     * @see ActionFrameArrangeBo
     * @see Map<String, String>
     */
    Map<String, String> getTacticNameMap(ActionFrameArrangeBo bo);

    /**
     * 获取推燕娱乐框架
     *
     * @param categoryId 类别id
     * @param keyWord    关键词
     * @return 列表<空间行动框架娱乐博>
     * @throws
     * @see String
     * @see String
     * @see List<SpaceActionFrameRecBo>
     */
    List<SpaceActionFrameRecBo> getTuiyanRecFrame(String categoryId, String keyWord);

    /**
     * 新增
     *
     * @param entity 实体
     * @throws
     * @see SpaceActionFrame
     */
    void add(SpaceActionFrame entity);

//    List<SpaceActionFrameRecBo> getNengliRecFrame(String categoryId, String keyWord);
}