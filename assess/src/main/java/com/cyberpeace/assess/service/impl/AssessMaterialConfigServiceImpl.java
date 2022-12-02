package com.cyberpeace.assess.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.assess.controller.req.AssessCaseConfigUpdateRes;
import com.cyberpeace.assess.controller.res.AssessCaseConfigRes;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.entity.AssessMaterialConfig;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.mapper.AssessCaseMapper;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.assess.service.AssessMaterialConfigService;
import com.cyberpeace.assess.dao.mapper.AssessMaterialConfigMapper;
import com.cyberpeace.assess.service.AssessMaterialService;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author dell
* @description 针对表【assess_case_config】的数据库操作Service实现
* @createDate 2022-11-21 13:55:57
*/
@Service
public class AssessMaterialConfigServiceImpl extends ServiceImpl<AssessMaterialConfigMapper, AssessMaterialConfig>
implements AssessMaterialConfigService {

    @Autowired
    private AssessCaseMapper assessCaseMapper;

    @Autowired
    private AssessMaterialConfigMapper assessMaterialConfigMapper;

    @Autowired
    private AssessMaterialMapper assessMaterialMapper;

    @Autowired
    private AssessCaseService assessCaseService;

    @Autowired
    private AssessMaterialService assessMaterialService;

    @Override
    public AssessCaseConfigRes getAssessMaterialConfig(String caseId, AuthContext authContext) {
        AssessCase assessCase = assessCaseMapper.selectById(caseId);
        if(null == assessCase){
            throw new BusinessException(AssessErrorCode.CASE_ID_ERROR);
        }

        AssessCaseConfigRes res = new AssessCaseConfigRes();
        res.setCaseId(caseId);
        res.setName(assessCase.getName());

        LambdaQueryWrapper<AssessMaterialConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssessMaterialConfig::getCaseId, caseId);

        HashMap<String, Object> filterMap = new HashMap<>();
        filterMap.put("case_id", caseId);
        AssessMaterialConfig assessMaterialConfigs = assessMaterialConfigMapper.selectOne(queryWrapper);
        String materialId = assessMaterialConfigs.getMaterialId();
        res.setResourceId(assessMaterialConfigs.getResourceId());
        JSONArray objects = JsonUtil.stringToArray(materialId);
        List<String> materialIds = JSONArray.parseArray(objects.toJSONString(), String.class);

        List<AssessCaseConfigRes> children = new ArrayList<>();

        List<AssessMaterial> assessMaterials = assessMaterialMapper.selectList(null);
        Map<String, AssessMaterial> collect = assessMaterials.stream().collect(Collectors.toMap(AssessMaterial::getResourceId, s -> s));
        materialIds.forEach(temp -> {
            AssessMaterial assessMaterial1 = collect.get(temp);
            if(null != assessMaterial1){
                AssessCaseConfigRes tmp = new AssessCaseConfigRes();
                String resourceId = assessMaterial1.getResourceId();
                tmp.setMaterialId(resourceId);
                tmp.setType(assessMaterial1.getMaterialType());
                tmp.setName(assessMaterial1.getName());
                tmp.setIndex(materialIds.indexOf(resourceId));
                children.add(tmp);
            }
        });

        res.setChildren(children);



        return res;
    }


    private List<AssessMaterialConfig> getConfigBo(String caseId, AuthContext authContext) {
        LambdaQueryWrapper<AssessMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssessMaterial::getCaseId, caseId);
        assessMaterialMapper.selectList(queryWrapper);
        return null;
    }

    @Override
    public void updateAssessMaterialConfig(AssessCaseConfigUpdateRes assessCaseConfigUpdateRes) {
        String caseId = assessCaseConfigUpdateRes.getCaseId();
        List<String> materialIds = assessCaseConfigUpdateRes.getMaterialId();

        assessCaseService.checkCaseId(caseId);
        assessMaterialService.checkMaterialIds(materialIds);

        AssessMaterialConfig assessMaterialConfig = getAssessMaterialConfig(caseId);


        assessMaterialConfig.setMaterialId(JsonUtil.arrayToString(materialIds));
        assessMaterialConfigMapper.updateById(assessMaterialConfig);
    }

    /**
     * 新增材料时 材料编排数据同步新增
     * @param assessMaterial
     * @return
     */
    @Override
    public AssessMaterialConfig setConfig(AssessMaterial assessMaterial) {
        String caseId = assessMaterial.getCaseId();
        String resourceId = assessMaterial.getResourceId();

        // 获取当前case关联的材料编排数据
        AssessMaterialConfig assessMaterialConfig = getAssessMaterialConfig(caseId);

        if(null == assessMaterialConfig){
            // 没有记录时 新增对象并设置材料id
            AssessMaterialConfig materialConfig = new AssessMaterialConfig();
            materialConfig.setCaseId(caseId);
            List<String> materialList = new ArrayList<>();
            materialList.add(resourceId);
            String materials = JsonUtil.arrayToString(materialList);
            materialConfig.setMaterialId(materials);
            assessMaterialConfigMapper.insert(materialConfig);
            return materialConfig;
        }
        // 已有记录时 在材料idlist末尾追加材料id
        String materialId = assessMaterialConfig.getMaterialId();
        List<String> materialList = JSONObject.parseArray(materialId, String.class);
        materialList.remove(resourceId);
        materialList.add(resourceId);
        String materials = JsonUtil.arrayToString(materialList);
        assessMaterialConfig.setMaterialId(materials);
        assessMaterialConfigMapper.updateById(assessMaterialConfig);
        return assessMaterialConfig;

    }

    private AssessMaterialConfig getAssessMaterialConfig(String caseId) {
        LambdaQueryWrapper<AssessMaterialConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssessMaterialConfig::getCaseId, caseId);
        AssessMaterialConfig assessMaterialConfig = assessMaterialConfigMapper.selectOne(queryWrapper);
        return assessMaterialConfig;
    }
}
