package com.cyberpeace.bs.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.lc.*;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.common.model.ListRsp;

/**
 * 远程Service
 *
 * @author SN
 * @date 2023/05/08
 */
public interface LcService {

    /**
     * 获取行为列表
     *
     * @param pageReq 分页请求体
     * @return 行为列表
     */
    ListRsp<LcBehavior> getLcBehaviorList(BehaviorPageReq pageReq);

    /**
     * 开启单个行为流量
     *
     * @param req 请求体
     */
    void startBehavior(ReleaseTaskReq req);

    /**
     * 停止单个行为流量
     *
     * @param req 请求体
     */
    void stopBehavior(StopTaskReq req);

    /**
     * 根据id获取用户行为列表
     *
     * @param behaviorIds  id集合
     * @param withVariable 是否返回变量
     * @return 用户行为列表
     */
    List<LcBehaviorRspBo> getLcBehaviorListByIds(Collection<String> behaviorIds,
                                                 boolean withVariable);

    /**
     * 通过id获取行为列表
     *
     * @param behaviorIds 行为id列表
     * @return {@code List<Behavior>}
     */
    List<Behavior> getBehaviorListByIds(Collection<String> behaviorIds);

    /**
     * 获取变量列表
     *
     * @param behaviorId 行为id
     * @return {@code VariableListBo}
     */
    VariableListBo getVariableList(String behaviorId);

    /**
     * 编辑行为变量
     *
     * @param req 请求体
     */
    void updateVariable(VariableModifyReq req);

    /**
     * 获取实体设备
     *
     * @param types 类型
     * @return
     */
    List<LcActualDevice> getActualDevices(List<Integer> types);

    /**
	 * 获取设备监控数据
	 *
	 * @param deviceId  设备id
	 * @param startTime 开始时间
	 * @return {@code LcMonitorRsp}
	 */
	LcMonitorRsp getDeviceMonitorData(String deviceId, String deviceIp, Long startTime);

    /**
     * 删除行为
     * 批量删除lc行为
     *
     * @param behaviorIds 行为id列表
     * @return {@code Boolean}
     */
    Boolean deleteBehavior(List<String> behaviorIds);

    /**
     * 导入 lc 行为文件
     *
     * @param file 文件
     * @return
     */
    String importBehavior(MultipartFile file);

    /**
     * 导出 lc 行为
     *
     * @param response    响应
     * @param behaviorIds 行为id列表
     */
    void exportBehavior(HttpServletResponse response, List<String> behaviorIds);

    /**
     * 获取录制行为跳转地址host
     *
     * @return {@code String}
     */
    String getLcHost();

    /**
     * 获取实时总带宽
     *
     * @return {@code Integer}
     */
    Integer getBandwidthSum();

	/**
	 * 通过uuid获取设备id
	 *
	 * @param uuid uuid
	 * @return {@code Integer}
	 */
	String getDeviceIdByUuid(String uuid);

	/**
	 * 通过uuid获取设备
	 *
	 * @param uuid uuid
	 * @return {@code Integer}
	 */
	LcDevice getDeviceOrExceptionByUuid(String uuid);

	/**
	 * 通过id获取设备
	 *
	 * @param id id
	 * @return {@code LcDevice}
	 */
	LcDevice getDeviceOrEmptyById(String id);

    /**
     * 通过excel导入行为
     *
     * @param file 文件
     * @return {@code String}
     */
    String importBehaviorByExcel(MultipartFile file);
}
