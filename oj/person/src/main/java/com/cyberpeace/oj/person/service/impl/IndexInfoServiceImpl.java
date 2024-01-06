package com.cyberpeace.oj.person.service.impl;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.oj.person.controller.req.IndexCategoryReq;
import com.cyberpeace.oj.person.controller.req.IndexCourseReq;
import com.cyberpeace.oj.person.controller.req.IndexReq;
import com.cyberpeace.oj.person.dao.entity.StudyRecord;
import com.cyberpeace.oj.person.dao.mapper.PersonCenterMapper;
import com.cyberpeace.oj.person.service.IndexInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IndexInfoServiceImpl implements IndexInfoService {
    private static final String filePath = "/cpms/index/IndexInfo.json";
    private static final String systemFilePath = "classpath:static/index/IndexInfo.json";
    @Autowired
    private PersonCenterMapper personCenterMapper;

    @Override
    public IndexReq getIndexInfo() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file = ResourceUtils.getFile(systemFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        List<IndexCourseReq> list = personCenterMapper.selectCourseStudyRecord(UserInfoContext.getUserContext().getUserId());
        Map<String, Integer> reqMap = list.stream().collect(Collectors.toMap(IndexCourseReq::getResourceId, IndexCourseReq::getStudyCount));
        String content = FileUtil.getContent(file);
        IndexReq indexReq = JSON.parseObject(content, IndexReq.class);
        for (IndexCategoryReq req : indexReq.getCategoryList()) {
            if (!CollectionUtils.isEmpty(req.getCourses())) {
                req.getCourses().forEach(course -> {
                    course.setCompletePercent(reqMap.getOrDefault(course.getResourceId(), 0)/course.getCourseNum());
                });
            }
        }
        return indexReq;
    }
}
