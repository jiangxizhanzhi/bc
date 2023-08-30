package com.cyberpeace.cr.evaluation.service.impl;

import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountBar;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountRingGraph;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalTaskLogMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalTrainTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalResultService;
import com.cyberpeace.situation.constant.ws.ActionTypeZHL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cyberpeace.common.util.EnumUtils.getListCode;

@Service
public class EvalResultServiceImpl implements EvalResultService {

    @Autowired
    private CrConstantsService crConstantsService;

    @Autowired
    private CrEvalTaskLogMapper crEvalTaskLogMapper;

    @Override
    public List<EvalEventCountBar> getEventCount(String evalTemplateId, Integer roleType) {
        List<EvalEventCountBar> result = new ArrayList<>();
        List<EvalEventCountBar> taskByRoomIdAndUsers = crEvalTaskLogMapper.getTaskByRoomIdAndType(evalTemplateId, CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
        Map<Integer, EvalEventCountBar> barMap = StreamApiUtils.toMap(taskByRoomIdAndUsers, EvalEventCountBar::getType, s -> s);
        List<Integer> listCode = getListCode(ActionTypeZHL.class);
        if (roleType.equals(CrRole.RbRoleGroup.RED.getCode())) {
            listCode = listCode.stream().filter(s -> s <= ActionTypeZHL.ZHL_ATTACK6.getCode()).collect(Collectors.toList());
        } else if (roleType.equals(CrRole.RbRoleGroup.BLUE.getCode())) {
            listCode = listCode.stream().filter(s -> s > ActionTypeZHL.ZHL_ATTACK6.getCode()).collect(Collectors.toList());
        } else {
            return result;
        }
        for (Integer tmp : listCode) {
            EvalEventCountBar rsp = new EvalEventCountBar();
            rsp.setCount(0);
            if (barMap.containsKey(tmp)) {
                EvalEventCountBar evalEventCountBar = barMap.get(tmp);
                rsp.setCount(evalEventCountBar.getCount());
            }
            String eventTypeStr = EnumUtils.getEnumDescByCode(tmp, ActionTypeZHL.class);
            rsp.setType(tmp);
            rsp.setEventName(eventTypeStr);
            result.add(rsp);
        }
        return result;
    }

    @Override
    public List<EvalEventCountRingGraph> getEventPercent(String evalTemplateId, Integer roleType) {

        ArrayList<EvalEventCountRingGraph> result = new ArrayList<>();
        List<EvalEventCountBar> taskByRoomIdAndUsers = crEvalTaskLogMapper.getTaskByRoomIdAndType(evalTemplateId, CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
        Map<Integer, EvalEventCountBar> barMap = StreamApiUtils.toMap(taskByRoomIdAndUsers, EvalEventCountBar::getType, s -> s);
        List<CrConstantsConfig> crConstantsConfigList;
        if (roleType.equals(CrRole.RbRoleGroup.RED.getCode())) {
            crConstantsConfigList = crConstantsService.getByType(-1);
        } else if (roleType.equals(CrRole.RbRoleGroup.BLUE.getCode())) {
            crConstantsConfigList = crConstantsService.getByType(-2);
        } else {
            return new ArrayList<>();
        }
        double sum = 0;
        for (CrConstantsConfig tmp : crConstantsConfigList) {
            Integer tmpType = tmp.getCode();
            EvalEventCountRingGraph rsp = new EvalEventCountRingGraph();
            rsp.setCount(0);
            if (barMap.containsKey(tmpType)) {
                EvalEventCountBar evalEventCountBar = barMap.get(tmpType);
                double count = evalEventCountBar.getCount() * 1.0;
                sum += count;
                rsp.setCount(count);
            }
            String eventTypeStr = EnumUtils.getEnumDescByCode(tmpType, ActionTypeZHL.class);
            rsp.setType(tmp.getCode());
            rsp.setEventName(eventTypeStr);
            result.add(rsp);
        }
        double finalSum = sum;
        result.forEach(item -> item.setCount(Math.abs(finalSum - 0.0) <= 0.000001 ? 0 : (item.getCount() / finalSum) * 100));
        return result;
    }
}
