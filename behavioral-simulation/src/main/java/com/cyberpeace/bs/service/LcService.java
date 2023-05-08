package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.lc.*;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.common.model.ListRsp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 龙测接口
 */
public interface LcService {

    /**
     * 获取行为列表
     *
     * @param pageReq
     * @return 行为列表
     */
    ListRsp<LcBehavior> getLcBehaviorList(BehaviorPageReq pageReq);

    /**
     * 开启单个行为流量
     */
    void startBehavior(ReleaseTaskReq req);

    /**
     * 停止单个行为流量
     */
    void stopBehavior(StopTaskReq req);

    /**
     * 根据id获取用户行为列表
     *
     * @param behaviorIds id集合
     * @return 用户行为列表
     */
    List<LcBehaviorRspBo> getLcBehaviorListByIds(Collection<String> behaviorIds,
                                                 boolean withVariable);

    List<Behavior> getBehaviorListByIds(Collection<String> behaviorIds);

    VariableListBo getVariableList(String behaviorId);

    /**
     * 编辑行为变量
     *
     * @param req
     */
    void updateVariable(VariableModifyReq req);

    /**
     * 获取实体设备
     *
     * @param types
     * @return
     */
    List<LcActualDevice> getActualDevices(List<Integer> types);

    /**
     * 获取设备监控数据
     *
     * @return
     */
    LcMonitorRsp getDeviceMonitorData(String deviceId, String deviceIp, Boolean isEntity,
                                      Long startTime);

    /**
     * 批量删除lc行为
     *
     * @param behaviorIds
     */
    Boolean deleteBehavior(List<String> behaviorIds);

    /**
     * 导入 lc 行为文件
     *
     * @param file
     * @return
     */
    String importBehavior(MultipartFile file);

    /**
     * 导出 lc 行为
     *
     * @param response
     * @param behaviorIds
     */
    void exportBehavior(HttpServletResponse response, List<String> behaviorIds);

    /**
     * 获取录制行为跳转地址host
     *
     * @return
     */
    String getLcHost();

    /**
     * 获取实时总带宽
     *
     * @return
     */
    Integer getBandwidthSum();

}
