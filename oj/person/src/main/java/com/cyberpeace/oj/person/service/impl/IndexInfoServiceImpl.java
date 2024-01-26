package com.cyberpeace.oj.person.service.impl;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.oj.person.controller.req.IndexCategoryReq;
import com.cyberpeace.oj.person.controller.req.IndexCourseReq;
import com.cyberpeace.oj.person.controller.req.IndexReq;
import com.cyberpeace.oj.person.dao.mapper.PersonCenterMapper;
import com.cyberpeace.oj.person.service.IndexInfoService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        Map<String, IndexCourseReq> reqMap = list.stream().collect(Collectors.toMap(IndexCourseReq::getResourceId, Function.identity()));
        String content = FileUtil.getContent(file);
        IndexReq indexReq = JSON.parseObject(content, IndexReq.class);
        int cov=0;
        for (IndexCategoryReq req : indexReq.getCategoryList()) {
            if (!CollectionUtils.isEmpty(req.getCourses())) {
                req.getCourses().forEach(course -> {
                    IndexCourseReq indexCourseReq = reqMap.get(course.getResourceId());
                    if (indexCourseReq == null) {
                        course.setCompletePercent(0);
                        course.setCoverage(0);
                        return;
                    }
                    Integer studyCount = indexCourseReq.getStudyCount()==null?0:indexCourseReq.getStudyCount();
                    course.setCompletePercent(studyCount*100/course.getCourseNum());
                    course.setDesc(indexCourseReq.getDesc());
                    course.setCoverage(course.getCompletePercent()*course.getWeight()/100);
                    req.setCoverage(req.getCoverage()+course.getCoverage());
                });
            }
            cov+=req.getCoverage();
        }
        indexReq.setCoverage(cov);
        return indexReq;
    }

    @Override
    public void setIndexInfo(String levelFileName) {
        File file = new File(filePath);
        if (!file.exists()) {
            log.error("build in file not found: {}", filePath);
            throw new BusinessException(ErrorCode.FILE_COPY_FAIL);
        }
        String indexFilePath = systemFilePath.replace("IndexInfo.json", levelFileName);
        try {
            file = ResourceUtils.getFile(indexFilePath);
        } catch (FileNotFoundException e) {
            log.error("get resource file error， file path: {}", indexFilePath);
            throw new BusinessException(ErrorCode.FILE_COPY_FAIL);
        }
        String indexFileAbsPath = file.getAbsolutePath();
        FileUtil.copyFile(indexFileAbsPath, filePath, false);
    }
}
