package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.entity.AssessTag;

import java.io.IOException;
import java.util.List;

/**
* @author dell
* @description 针对表【assess_case】的数据库操作Service
* @createDate 2022-11-21 13:52:10
*/
public interface AssessCaseService extends IService<AssessCase> {

    String getTagContent(String tags, List<AssessTag> assessTags);

    AssessCase checkCaseId(String resourceId);

    void checkNameRepeat(String name, String id);

    String makeDumpFile(String resourceId, AssessCase tempDir) throws IOException;
}
