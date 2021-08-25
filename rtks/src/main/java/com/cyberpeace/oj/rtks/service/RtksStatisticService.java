package com.cyberpeace.oj.rtks.service;

import com.cyberpeace.oj.rtks.service.bo.RtksRadarBo;
import java.util.List;

/**
 * rtks数据统计功能
 */
public interface RtksStatisticService {

    /**
     * 计算指定岗位的匹配度
     * @param roleId
     * @param userId
     * @return 匹配度百分比
     */
    String roleMatchPercent(String roleId, String userId);

    /**
     * 计算岗位雷达图
     * @param roleId
     * @param userId
     * @return
     */
    List<RtksRadarBo> calculateRadar(String roleId, String userId);
}
