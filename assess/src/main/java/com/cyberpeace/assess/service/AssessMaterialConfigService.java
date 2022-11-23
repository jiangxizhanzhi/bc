package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.controller.req.AssessCaseConfigUpdateRes;
import com.cyberpeace.assess.controller.res.AssessCaseConfigRes;
import com.cyberpeace.assess.dao.entity.AssessMaterialConfig;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.common.model.AuthContext;

/**
* @author dell
* @description 针对表【assess_case_config】的数据库操作Service
* @createDate 2022-11-21 13:55:57
*/
public interface AssessMaterialConfigService extends IService<AssessMaterialConfig> {

    AssessCaseConfigRes getAssessMaterialConfig(String caseId, AuthContext authContext);

    void updateAssessMaterialConfig(AssessCaseConfigUpdateRes configBo);

    AssessMaterialConfig setConfig(AssessMaterial assessMaterial);
}
