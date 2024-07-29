package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalSystemReq;
import com.cyberpeace.cr.evaluation.dao.mapper.GXPTIndexMapper;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 共性平台方法-同步数据
 */
@Service
@Slf4j
public class GXPTIndexServiceImpl {

    @Autowired
    private GXPTIndexMapper gxptIndexMapper;

    private static final String ADD_INDEX_URL = "http://ip:port/hierarchyTemplate/addHierarchyTemplateV1";// 共性平台添加指标接口

    private static final String DELETE_INDEX_URL = "http://ip:port/hierarchyTemplate/delHierarchyTemplate";// 共性平台添加指标接口

    // 共性平台同步指标
    public void addIndex(EvalSystemReq evalSystemReq) {
        String url = ADD_INDEX_URL;
        // 获取所有指标数据
        List<IndexContext> newIndexes = new ArrayList<>();
        getAllIndex(evalSystemReq, newIndexes);

        JSONObject request = new JSONObject();
        request.put("templateName", evalSystemReq.getName());
        request.put("quotaRootName", null);
        // todo
        request.put("quota", newIndexes);

        JSONObject resultMap = null;
        try {
            resultMap = HttpClientUtil.post(url, request, JSONObject.class);
        } catch (Exception e) {
            log.error("调用共性平台添加指标接口失败", e);
        }
        // 根据resource_id查询团队训练房间并修改
        String returnInfo = resultMap != null ? String.valueOf(resultMap) : "";
        String returnData = resultMap != null ? String.valueOf(resultMap.get("data")) : "";
        String code = resultMap != null ? String.valueOf(resultMap.get("code")) : "";
        Boolean isSychronized = (code != null && "200".equals(code));
        // 收集newIndexes中所有的indexId数据
        List<String> indexIds = newIndexes.stream().map(IndexContext::getIndexId).collect(Collectors.toList());
        if(indexIds != null && !indexIds.isEmpty()){
            gxptIndexMapper.updateIndexDataByResourceIds(indexIds, isSychronized.toString(), returnData, returnInfo);
        }
    }

    private void getAllIndex(EvalSystemReq evalSystemReq, List<IndexContext> newIndexes){
        List<IndexContext> firstIndexes = evalSystemReq.getFirstIndexes();
        List<IndexContext> secondIndexes = evalSystemReq.getSecondIndexes();
        List<IndexContext> thirdIndexes = evalSystemReq.getThirdIndexes();
        newIndexes.addAll(firstIndexes);
        newIndexes.addAll(secondIndexes);
        newIndexes.addAll(thirdIndexes);
    }

    public void deleteIndex(List<String> evalSystemIds) {

        // 根据evalSystemIds 查询所有index
        List<String> returnData = gxptIndexMapper.selectReturnDataByEvalSystemIds(evalSystemIds);

        String url = DELETE_INDEX_URL;
        JSONObject request = new JSONObject();
        request.put("***待定", returnData);

        JSONObject resultMap = null;
        try {
            resultMap = HttpClientUtil.post(url, request, JSONObject.class);
        } catch (Exception e) {
            log.error("调用共性平台添加指标接口失败", e);
        }
    }
}
