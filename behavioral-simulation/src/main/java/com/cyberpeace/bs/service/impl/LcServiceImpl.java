package com.cyberpeace.bs.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.behavior.LcBehaviorPageRes;
import com.cyberpeace.bs.service.bo.lc.*;
import com.cyberpeace.bs.service.bo.variable.Variable;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.bs.service.bo.variable.VariableSimple;
import com.cyberpeace.bs.utils.BsUtil;
import com.cyberpeace.bs.utils.LcHttpClient;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.LcServiceException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 远程Service实现
 *
 * @author SN
 * @date 2023/05/08
 */
@Service
@Slf4j
public class LcServiceImpl implements LcService {

    /**
     * 龙测API服务主机+端口
     */
    private String LC_HOST_PORT;

    /**
     * 根据id查询行为列表
     */
    private String BEHAVIOR_LIST_URL = "api/v1/findFlowChart";

    /**
     * 启动行为
     */
    private String RELEASE_TASK = "api/v1/save";

    /**
     * 中止行为
     */
    private String STOP_TASK = "api/v1/stopTask";

    /**
     * 查询设备
     */
    private String DEVICE_URL = "api/v1/devices/find-by-page";

	private String DEVICE_BY_ID = "api/v1/devices/";

    /**
     * 行为列表
     */
    private String BEHAVIOR_PAGE = "api/v1/newList";

    /**
     * 查询行为的变量
     */
    private String BEHAVIOR_VARIABLE = "api/v1/flowData/";

    /**
     * 更新行为的变量
     */
    private String BEHAVIOR_VARIABLE_UPDATE = "api/v1/updateFlowData";

    /**
     * 获取实体设备列表
     */
    private String ACTUAL_DEVICE_LIST = "api/v1/devices/findNewList";

    /**
     * 获取行为仪表监控
     */
    private String MONITOR_URL = "api/v1/monitor/findByDevice";

    /**
     * 删除 lc 行为
     */
    private String DELETE_BEHAVIOR_URL = "api/v1/flowchart/delete-batch";

    /**
     * 导出 lc 行为
     */
    private String BEHAVIOR_EXPORT = "api/v1/flowcharts/export";

    /**
     * 行为导入
     */
    private String BEHAVIOR_IMPORT_GZ = "api/v1/flowcharts/behaviorImport";

    /**
     * excel行为导入
     */
    private String BEHAVIOR_IMPORT_XLS = "api/v1/sainingNlpImport";

    /**
     * 实时总带宽
     */
    private String BANDWIDTH_SUM = "api/v1/monitor/getAll";

    @PostConstruct
    public void init() {
        // 获取远程服务地址
        LC_HOST_PORT = SystemPropUtil.getProp("LC_HOST_PORT");
        if (StringUtils.isEmpty(LC_HOST_PORT)) {
            LC_HOST_PORT = "http://10.4.1.138/";
        }
        log.info("LC_HOST_PORT: {}", LC_HOST_PORT);
    }

    @Override
    public ListRsp<LcBehavior> getLcBehaviorList(BehaviorPageReq pageReq) {
        String targetUrl = LC_HOST_PORT + BEHAVIOR_PAGE;
        LcBehaviorListReqBody body = new LcBehaviorListReqBody();
        // 设置分页
        body.setPage(pageReq.getPage());
        body.setSize(pageReq.getPerPage());
        // 设置名称搜索
        body.setFlowChartName(pageReq.getPattern());
        // 设置网络类型
        body.setNetModel(pageReq.getNetworkType());
        // 设置设备类型
        body.setDeviceType(pageReq.getAccessOs());
        // 设置应用类型
        body.setAppType(pageReq.getAppType());
        // 设置是否实体
        body.setIsActual(pageReq.getIsActual());
        // 获取远程行为数据
        LcBehaviorPageRes lcBehaviorPageRes = LcHttpClient.post(targetUrl, body,
                LcBehaviorPageRes.class);
        ListRsp<LcBehavior> res = new ListRsp<>();
        List<LcBehavior> list = Lists.newArrayList();
        // 转换为本地行为数据类型
        for (LcBehaviorRspBo record : lcBehaviorPageRes.getRecords()) {
            LcBehavior lcBehavior = createLcBehavior(record);
            list.add(lcBehavior);
        }
        // 设置分页数据格式
        res.setList(list);
        res.setTotal(lcBehaviorPageRes.getTotal());
        res.setCurrentPage(pageReq.getPage());
        res.setLastPage(res.getTotal() / pageReq.getPerPage() + 1);
        return res;
    }

    @Override
    public void startBehavior(ReleaseTaskReq req) {
        // 组装请求地址
        String targetUrl = LC_HOST_PORT + RELEASE_TASK;
        // 日志输出请求体
        log.info("Start behavior request: {}", JsonUtil.toJsonString(req));
        // 发送请求
        LcHttpClient.post(targetUrl, req, String.class);
    }

    @Override
    public void stopBehavior(StopTaskReq req) {
        // 组装请求地址
        String targetUrl = LC_HOST_PORT + STOP_TASK;
        // 日志输出请求体
        log.info("Stop behavior request: {}", JsonUtil.toJsonString(req));
        // 发送请求
        LcHttpClient.post(targetUrl, req, String.class);
    }

    /**
     * 将远程请求行为数据转换为前端可展示的数据结构
     *
     * @param rspBo 远程响应数据
     * @return {@code LcBehavior}
     */
    private LcBehavior createLcBehavior(LcBehaviorRspBo rspBo) {
        LcBehavior lcBehavior = new LcBehavior();
        lcBehavior.setId(rspBo.getId());
        lcBehavior.setName(rspBo.getFlowChartName());
        lcBehavior.setAccessOs(rspBo.getDeviceType());
        lcBehavior.setNetworkType(rspBo.getNetModel());
        lcBehavior.setStepCount(rspBo.getStepNum());
        lcBehavior.setTimeCost(BsUtil.s2Min(rspBo.getConsumeTime()));
        lcBehavior.setCreateTime(rspBo.getCreateTime());
        lcBehavior.setCreatorName(rspBo.getCreatorName());
        lcBehavior.getApplication().add(rspBo.getApplication());
        lcBehavior.setTag(BsUtil.parseTags(rspBo.getTag()));
        lcBehavior.setAppType(rspBo.getAppType());
        lcBehavior.setIsActual(rspBo.getIsActual());
        return lcBehavior;
    }

    @Override
    public List<LcBehaviorRspBo> getLcBehaviorListByIds(Collection<String> behaviorIds,
                                                        boolean withVariable) {
        // 组装请求地址
        String targetUrl = LC_HOST_PORT + BEHAVIOR_LIST_URL;
        LcBehaviorQuery query = new LcBehaviorQuery();
        // 设置请求的行为id列表
        query.addIds(behaviorIds);
        // 要求返回变量数据
        if (withVariable) {
            query.setWithVariable(true);
        }
        // 发送请求
        String rsp = LcHttpClient.post(targetUrl, query, String.class);
        // 解析响应
        List<LcBehaviorRspBo> behaviorRspBos = JsonUtil.toJsonObjectArray(rsp,
                LcBehaviorRspBo.class);

        return behaviorRspBos;
    }

    @Override
    public List<Behavior> getBehaviorListByIds(Collection<String> behaviorIds) {
        List<LcBehaviorRspBo> lcBehaviorRspBos = getLcBehaviorListByIds(behaviorIds, false);
        // 数据格式转换
        return lcBehaviorRspBos.stream().map(this::createBehavior).collect(Collectors.toList());
    }

    /**
     * 创建行为
     * 从远程行为读取数据，复制到本地行为数据
     *
     * @param rspBo 远程响应数据
     * @return {@code Behavior}
     */
    private Behavior createBehavior(LcBehaviorRspBo rspBo) {
        Behavior behavior = new Behavior();
        behavior.setAccessOs(rspBo.getDeviceType());
        behavior.setCreateTime(rspBo.getCreateTime());
        behavior.setCreatorName(rspBo.getCreatorName());
        behavior.setName(rspBo.getFlowChartName());
        behavior.setNetworkType(rspBo.getNetModel());
        behavior.setStepCount(rspBo.getStepNum());
        behavior.setTag(BsUtil.parseTags(rspBo.getTag()));
        behavior.setId(rspBo.getId());
        behavior.setTimeCost(BsUtil.s2Min(rspBo.getConsumeTime()));

        behavior.setAppType(rspBo.getAppType());
        behavior.setIsActual(rspBo.getIsActual());
        return behavior;
    }

    @Override
    public VariableListBo getVariableList(String behaviorId) {
        // 组装请求地址
        String targetUrl = LC_HOST_PORT + BEHAVIOR_VARIABLE + behaviorId;
        // 发送请求
        LcVariableRsp res = LcHttpClient.rawGet(targetUrl, LcVariableRsp.class);
        List<LcVariableRspBo> lcVariableRspBos = res.getVariables();
        VariableListBo listBo = new VariableListBo();
        // 组装行为变量列表数据
        listBo.setBehaviorId(behaviorId);
        if (CollectionUtils.isEmpty(lcVariableRspBos)) {
            listBo.setVariables(Lists.newArrayList());
            return listBo;
        }
        List<Variable> variables = Lists.newArrayList();
        lcVariableRspBos.forEach(lcVariable -> {
            Variable variable = new Variable();
            variable.setId(lcVariable.getDataId());
            variable.setName(lcVariable.getName());
            variable.setNote(lcVariable.getNote());
            variable.setValue(lcVariable.getVarValue());
            variable.setUpdateTime(lcVariable.getUpdateTime());
            variables.add(variable);
        });
        listBo.setVariables(variables);
        return listBo;
    }

    @Override
    public void updateVariable(VariableModifyReq req) {
        // 请求地址
        String targetUrl = LC_HOST_PORT + BEHAVIOR_VARIABLE_UPDATE;
        // 组装请求体
        LcVariableModifyReq body = new LcVariableModifyReq();
        body.setFlowChartId(new Long(req.getBehaviorId()));
        // 循环修改的变量
        for (VariableSimple variable : req.getVariables()) {
            LcVariableModifyBo lcVariable = new LcVariableModifyBo();
            lcVariable.setDataId(new Long(variable.getId()));
            lcVariable.setName(variable.getName());
            lcVariable.setVarValue(variable.getValue());
            lcVariable.setNote(variable.getNote());
            body.getVariables().add(lcVariable);
        }
        // 发送更新请求
        LcHttpClient.patch(targetUrl, body, String.class);
    }

    @Override
    public List<LcActualDevice> getActualDevices(List<Integer> types) {
        // 请求地址
        String targetUrl = LC_HOST_PORT + ACTUAL_DEVICE_LIST;
        // 生成请求体
        LcActualDeviceReq body = new LcActualDeviceReq();
        body.setDeviceTypes(types);
        // 发送请求
        String res = LcHttpClient.post(targetUrl, body, String.class);
        // 解析响应
        List<LcActualDevice> devices = JsonUtil.toJsonObjectArray(res, LcActualDevice.class);
        return devices;
    }

    @Override
    public LcMonitorRsp getDeviceMonitorData(String deviceId, String deviceIp, Boolean isEntity,
                                             Long startTime) {
        // 请求地址
        String targetUrl = LC_HOST_PORT + MONITOR_URL;
        // 组装请求体
        LcMonitorReq req = new LcMonitorReq();
        req.setDeviceId(BsUtil.getLcDeviceId(deviceId, !isEntity));
        req.setDeviceIp(deviceIp);
        req.setIsUsingIp(!isEntity);
        // 设置监控数据开始时间戳
        req.setStartTime(startTime);
        // 日志记录请求
        log.info("monitor query param={}, date={}", JsonUtil.toJsonString(req),
                JsonUtil.toJsonString(TimeUtil.format1(new Date())));
        // 发送请求
        LcMonitorRsp rsp = LcHttpClient.post(targetUrl, req, LcMonitorRsp.class);
        // 日志记录响应
        log.info("monitor rsp={}", JsonUtil.toJsonString(rsp));
        return rsp;
    }

    @Override
    public Boolean deleteBehavior(List<String> behaviorIds) {
        // 请求地址
        String targetUrl = LC_HOST_PORT + DELETE_BEHAVIOR_URL;
        // 要删除的行为id列表
        List<Long> lcBehaviorIds = BsUtil.getLcBehaviorIds(behaviorIds);
        // 发送请求
        Boolean rsp = LcHttpClient.post(targetUrl, lcBehaviorIds, Boolean.class);
        return rsp;
    }

    @Override
    public String importBehavior(MultipartFile file) {
        // 请求地址
        String url = LC_HOST_PORT + BEHAVIOR_IMPORT_GZ;
        // 发送文件请求
        String rsp = LcHttpClient.uploadFile2Lc(url, file);
        return rsp;
    }

    @Override
    public void exportBehavior(HttpServletResponse response, List<String> behaviorIds) {
        // 请求地址
        String targetUrl = LC_HOST_PORT + BEHAVIOR_EXPORT;
        // 将字符串id转换为long型id
        List<Long> lcBehaviorIds = BsUtil.getLcBehaviorIds(behaviorIds);
        // 发送请求
        LcHttpClient.exportBehavior(response, lcBehaviorIds, targetUrl);
    }

    @Override
    public String getLcHost() {
        return LC_HOST_PORT;
    }

    @Override
    public Integer getBandwidthSum() {
        // 请求地址
        String targetUrl = LC_HOST_PORT + BANDWIDTH_SUM;
        // 获取实时总带宽数据
        Integer rsp = LcHttpClient.rawGet(targetUrl, Integer.class);
        return rsp;
    }

	@Override
	public String getDeviceIdByUuid(String uuid) {
		// 请求地址
		String targetUrl = LC_HOST_PORT + DEVICE_URL;
		// req
		LcDeviceReq req = new LcDeviceReq();
		req.setUuid(uuid);
		// get device
		LcDevicePageRes res = LcHttpClient.post(targetUrl, req, LcDevicePageRes.class);
		List<LcDevice> deviceList = res.getRecords();

		if (CollectionUtils.isEmpty(deviceList)) {
			log.error("cant find device by uuid: [{}]", uuid);
			throw new BusinessException(BsErrorCode.DEVICE_NOT_FOUND);
		}
		// get device
		LcDevice device = deviceList.get(0);
		if (StringUtils.isBlank(device.getId())) {
			log.error("empty device id by uuid: [{}]", uuid);
			throw new BusinessException(BsErrorCode.EMPTY_DEVICE_ID);
		}
		return device.getId();
	}

	@Override
	public LcDevice getDeviceOrEmptyByUuid(String uuid) {
		// 请求地址
		String targetUrl = LC_HOST_PORT + DEVICE_URL;
		// req
		LcDeviceReq req = new LcDeviceReq();
		req.setUuid(uuid);
		// get device
		LcDevicePageRes res = LcHttpClient.post(targetUrl, req, LcDevicePageRes.class);
		List<LcDevice> deviceList = res.getRecords();

		if (CollectionUtils.isEmpty(deviceList)) {
			return new LcDevice();
		}
		// get device
		return deviceList.get(0);
	}

	@Override
	public LcDevice getDeviceOrEmptyById(String id) {
		// 请求地址
		String targetUrl = LC_HOST_PORT + DEVICE_BY_ID + id;
		// get device
		try {
			return LcHttpClient.rawGet(targetUrl, LcDevice.class);
		} catch (LcServiceException e) {
			return new LcDevice();
		}
	}

    @Override
    public String importBehaviorByExcel(MultipartFile file) {
        // 请求地址
        String targetUrl = LC_HOST_PORT + BEHAVIOR_IMPORT_XLS;
        // 发送文件请求
        String rsp = LcHttpClient.uploadFile2Lc(targetUrl, file);
        return rsp;
    }
}