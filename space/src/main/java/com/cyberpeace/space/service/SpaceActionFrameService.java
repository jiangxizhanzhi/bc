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
 * @date 2024/11/05
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

    void saveFrame(ActionFrameArrangeBo req);

    void recordUsage(String type);

    ActionFrameArrangeBo getFrame(String resourceId);

    List<SpaceActionFrameBo> searchFrame(String req);

    Map<String, String> getTacticNameMap(ActionFrameArrangeBo bo);

    List<SpaceActionFrameRecBo> getTuiyanRecFrame(String categoryId, String keyWord);

    void add(SpaceActionFrame entity);

//    List<SpaceActionFrameRecBo> getNengliRecFrame(String categoryId, String keyWord);
}