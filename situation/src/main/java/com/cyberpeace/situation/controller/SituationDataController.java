package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.dao.mapper.AttackMessageHistoryMapper;
import com.cyberpeace.situation.dao.mapper.DynamicSchemeMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "态势拓扑数据")
@RestController
@RequestMapping("/situation/data")
public class SituationDataController {

    @Autowired
    private DynamicSchemeMapper dynamicSchemeMapper;

    @Autowired
    private AttackMessageHistoryMapper attackMessageHistoryMapper;

    @ApiOperation("清除态势数据")
    @GetMapping("clear")
    public BaseRsp<Boolean>
    clearSceneData(@ApiParam(name = "scene_id", value = "场景编号",
            required = true) @RequestParam(value = "scene_id") String sceneId) {
        dynamicSchemeMapper.deleteDefenseBySceneId(sceneId);
        attackMessageHistoryMapper.deleteMessageBySceneId(sceneId);
        return new BaseRsp<>(true);
    }
}
