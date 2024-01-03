package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.oj.course.controller.req.IndexReq;
import com.cyberpeace.oj.course.service.IndexInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Service
public class IndexInfoServiceImpl implements IndexInfoService {
    private static final String filePath = "/cpms/index/IndexInfo.json";
    private static final String systemFilePath = "classpath:static/index/IndexInfo.json";

    @Override
    public IndexReq getIndexInfo() throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            file = ResourceUtils.getFile(systemFilePath);
        }
        String content = FileUtil.getContent(file);
        IndexReq indexReq = JSON.parseObject(content, IndexReq.class);
        return indexReq;
    }
}
