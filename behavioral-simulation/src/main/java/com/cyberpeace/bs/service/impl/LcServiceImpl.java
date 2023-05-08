package com.cyberpeace.bs.service.impl;

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
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private String DEVICE_URL = "api/v1/devices/find-by-page";

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
     * 实时总带宽
     */
    private String BANDWIDTH_SUM = "api/v1/monitor/getAll";

    @PostConstruct
    public void init() {
        LC_HOST_PORT = SystemPropUtil.getProp("LC_HOST_PORT");
        if (StringUtils.isEmpty(LC_HOST_PORT)) {
            LC_HOST_PORT = "http://10.4.1.138/";
        }
        log.info("LC_HOST_PORT: {}", LC_HOST_PORT);
        // BEHAVIOR_LIST_URL = LC_HOST_PORT +
        // ConfigUtil.getContextProperty("lc.behavior.list");
        // TARGET_LIST_URL = LC_HOST_PORT +
        // ConfigUtil.getContextProperty("lc.target.list");
    }

    @Override
    public ListRsp<LcBehavior> getLcBehaviorList(BehaviorPageReq pageReq) {
        String targetUrl = LC_HOST_PORT + BEHAVIOR_PAGE;
        LcBehaviorListReqBody body = new LcBehaviorListReqBody();
        body.setPage(pageReq.getPage());
        body.setSize(pageReq.getPerPage());
        body.setFlowChartName(pageReq.getPattern());
        body.setNetModel(pageReq.getNetworkType());
        body.setDeviceType(pageReq.getAccessOs());
        body.setAppType(pageReq.getAppType());
        body.setIsActual(pageReq.getIsActual());
        LcBehaviorPageRes lcBehaviorPageRes = LcHttpClient.post(targetUrl, body,
                LcBehaviorPageRes.class);
        ListRsp<LcBehavior> res = new ListRsp<>();
        List<LcBehavior> list = Lists.newArrayList();
        for (LcBehaviorRspBo record : lcBehaviorPageRes.getRecords()) {
            LcBehavior lcBehavior = createLcBehavior(record);
            list.add(lcBehavior);
        }
        res.setList(list);
        res.setTotal(lcBehaviorPageRes.getTotal());
        res.setCurrentPage(pageReq.getPage());
        res.setLastPage(res.getTotal() / pageReq.getPerPage() + 1);
        return res;
    }

    @Override
    public void startBehavior(ReleaseTaskReq req) {
        String targetUrl = LC_HOST_PORT + RELEASE_TASK;
        log.info("Start behavior request: {}", JsonUtil.toJsonString(req));
        LcHttpClient.post(targetUrl, req, String.class);
    }

    @Override
    public void stopBehavior(StopTaskReq req) {
        String targetUrl = LC_HOST_PORT + STOP_TASK;
        log.info("Stop behavior request: {}", JsonUtil.toJsonString(req));
        LcHttpClient.post(targetUrl, req, String.class);
    }

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
        String targetUrl = LC_HOST_PORT + BEHAVIOR_LIST_URL;
        LcBehaviorQuery query = new LcBehaviorQuery();
        query.addIds(behaviorIds);
        if (withVariable) {
            query.setWithVariable(true);
        }
        String rsp = LcHttpClient.post(targetUrl, query, String.class);
        List<LcBehaviorRspBo> behaviorRspBos = JsonUtil.toJsonObjectArray(rsp,
                LcBehaviorRspBo.class);

        return behaviorRspBos;
    }

    @Override
    public List<Behavior> getBehaviorListByIds(Collection<String> behaviorIds) {
        List<LcBehaviorRspBo> lcBehaviorRspBos = getLcBehaviorListByIds(behaviorIds, false);
        return lcBehaviorRspBos.stream().map(this::createBehavior).collect(Collectors.toList());
    }

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
        String targetUrl = LC_HOST_PORT + BEHAVIOR_VARIABLE + behaviorId;
        LcVariableRsp res = LcHttpClient.rawGet(targetUrl, LcVariableRsp.class);
        List<LcVariableRspBo> lcVariableRspBos = res.getVariables();
        VariableListBo listBo = new VariableListBo();
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
        String targetUrl = LC_HOST_PORT + BEHAVIOR_VARIABLE_UPDATE;
        LcVariableModifyReq body = new LcVariableModifyReq();
        body.setFlowChartId(new Long(req.getBehaviorId()));

        for (VariableSimple variable : req.getVariables()) {
            LcVariableModifyBo lcVariable = new LcVariableModifyBo();
            lcVariable.setDataId(new Long(variable.getId()));
            lcVariable.setName(variable.getName());
            lcVariable.setVarValue(variable.getValue());
            lcVariable.setNote(variable.getNote());
            body.getVariables().add(lcVariable);
        }

        LcHttpClient.patch(targetUrl, body, String.class);
    }

    @Override
    public List<LcActualDevice> getActualDevices(List<Integer> types) {
        String targetUrl = LC_HOST_PORT + ACTUAL_DEVICE_LIST;
        LcActualDeviceReq body = new LcActualDeviceReq();
        body.setDeviceTypes(types);
        String res = LcHttpClient.post(targetUrl, body, String.class);
        List<LcActualDevice> devices = JsonUtil.toJsonObjectArray(res, LcActualDevice.class);
        return devices;
    }

    @Override
    public LcMonitorRsp getDeviceMonitorData(String deviceId, String deviceIp, Boolean isEntity,
                                             Long startTime) {

        String targetUrl = LC_HOST_PORT + MONITOR_URL;
        LcMonitorReq req = new LcMonitorReq();
        req.setDeviceId(BsUtil.getLcDeviceId(deviceId, !isEntity));
        req.setDeviceIp(deviceIp);
        req.setIsUsingIp(!isEntity);
        req.setStartTime(startTime);
        log.info("monitor query param={}, date={}", JsonUtil.toJsonString(req),
                JsonUtil.toJsonString(TimeUtil.format1(new Date())));
        LcMonitorRsp rsp = LcHttpClient.post(targetUrl, req, LcMonitorRsp.class);
        log.info("monitor rsp={}", JsonUtil.toJsonString(rsp));
        return rsp;
    }

    @Override
    public Boolean deleteBehavior(List<String> behaviorIds) {
        String targetUrl = LC_HOST_PORT + DELETE_BEHAVIOR_URL;
        List<Long> lcBehaviorIds = BsUtil.getLcBehaviorIds(behaviorIds);
        Boolean rsp = LcHttpClient.post(targetUrl, lcBehaviorIds, Boolean.class);
        return rsp;
    }

    @Override
    public String importBehavior(MultipartFile file) {
        String url = LC_HOST_PORT + BEHAVIOR_IMPORT_GZ;
        String rsp = LcHttpClient.uploadFile2Lc(url, file);
        return rsp;
    }

    @Override
    public void exportBehavior(HttpServletResponse response, List<String> behaviorIds) {
        String targetUrl = LC_HOST_PORT + BEHAVIOR_EXPORT;
        List<Long> lcBehaviorIds = BsUtil.getLcBehaviorIds(behaviorIds);
        LcHttpClient.exportBehavior(response, lcBehaviorIds, targetUrl);
    }

    @Override
    public String getLcHost() {
        return LC_HOST_PORT;
    }

    @Override
    public Integer getBandwidthSum() {
        String targetUrl = LC_HOST_PORT + BANDWIDTH_SUM;
        Integer rsp = LcHttpClient.rawGet(targetUrl, Integer.class);
        return rsp;
    }
}