package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.entity.AssessTag;

import java.util.List;
import java.util.Map;

/**
* @author dell
* @description 针对表【assess_case】的数据库操作Service
* @createDate 2022-11-21 13:52:10
*/
public interface AssessCaseService extends IService<AssessCase> {

    String getTagContent(String tags);

    AssessCase checkCaseId(String resourceId);

    void checkNameRepeat(String name, String id);
}
