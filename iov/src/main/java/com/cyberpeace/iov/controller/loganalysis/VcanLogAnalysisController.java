package com.cyberpeace.iov.controller.loganalysis;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.iov.service.impl.VcanLogAnalysisServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "vcan日志分析")
@RestController
@RequestMapping("/log/vcanLog/analyse")
public class VcanLogAnalysisController {

    @Autowired
    private VcanLogAnalysisServiceImpl vcanLogAnalysisService;

    @ApiOperation(value = "vcan日志导入", notes = "")
    @PostMapping("/importVcanLog")
    public BaseRsp importVcanLog(@RequestParam(name = "file") MultipartFile file,
                                 @RequestParam(name = "restricted_size", required = false) Long size,
                                 @RequestParam(name = "table_name") String tableName,
                                 @RequestParam(name = "scene_id", required = false) String sceneId,
                                 @RequestParam(name = "element_id", required = false) String elementId) {
        // restricted_size:日志需要截取的字节数
        // 会自动缓存上一次提取的字节数,从上一次提取的字节数开始提取(根据tableName做缓存)
        vcanLogAnalysisService.importVcanLog(file, size, tableName, sceneId, elementId);
        return new BaseRsp();
    }

    @ApiOperation(value = "vcan日志列表", notes = "")
    @GetMapping("/getVcanLog")
    public BaseRsp getVcanLog(@RequestParam(name = "table_name") String tableName,
                              @RequestParam(name = "scene_id", required = false) String sceneId,
                              @RequestParam(name = "element_id", required = false) String elementId,
                              @RequestParam(name = "page_num", required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(name = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        JSONObject result = vcanLogAnalysisService.getVcanLog(tableName, sceneId, elementId, pageNum, pageSize);
        return new BaseRsp(result);
    }
}
