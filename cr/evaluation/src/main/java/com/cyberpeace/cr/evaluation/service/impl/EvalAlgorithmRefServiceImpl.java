package com.cyberpeace.cr.evaluation.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmRefService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvalAlgorithmRefServiceImpl extends ServiceImpl<EvalAlgorithmRefMapper, EvalAlgorithmRef> implements EvalAlgorithmRefService {

    private static final String PARAM_TYPE = "param_type";

    private static final String PARAM_VALUE = "default_value";

    @Override
    public List<Serializable> buildOtherArguments(String argumentsString) {
        List<Serializable> args = Lists.newArrayList();
        List<JSONObject> params = JsonUtil.toJsonObjectArray(argumentsString, JSONObject.class);

        params.forEach(param -> {
            Integer type = param.getInteger(PARAM_TYPE);
            if (type.equals(EvalAlgorithmRef.Params.NUMBER.getCode())) {
                try {
                    args.add(param.getInteger(PARAM_VALUE));
                } catch (NumberFormatException e) {
                    args.add(param.getDouble(PARAM_VALUE));
                }
            } else if (type.equals(EvalAlgorithmRef.Params.STRING.getCode())) {
                args.add(param.getString(PARAM_VALUE));
            } else if (type.equals(EvalAlgorithmRef.Params.BOOL.getCode())) {
                args.add(param.getBoolean(PARAM_VALUE));
            } else if (type.equals(EvalAlgorithmRef.Params.IP.getCode())) {
                args.add(param.getString(PARAM_VALUE));
            }
        });
        return args;
    }
}
