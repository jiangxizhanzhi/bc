package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.FrameAddReq;
import com.cyberpeace.oj.rtks.controller.req.LevelAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.service.LevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/frame")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @ApiOperation("新增框架")
    @PostMapping()
    public BaseRsp<Level> addFrame(@RequestBody @Validated FrameAddReq frameAddReq) {
        frameAddReq.setResourceId(null);

        Level frame = new Level();
        BeanUtils.copyProperties(frameAddReq, frame);

        levelService.save(frame);

        return new BaseRsp<>(frame);
    }

    @ApiOperation("修改框架")
    @PutMapping()
    public BaseRsp<Level> modifyTask(@RequestBody @Validated FrameAddReq frameAddReq) {
        Level frame = new Level();
        BeanUtils.copyProperties(frameAddReq, frame);
        levelService.updateById(frame);
        return new BaseRsp<>(frame);
    }

    @ApiOperation("删除框架")
    @DeleteMapping()
    public BaseRsp<Level> deleteFrame(@RequestBody @Validated DeleteReq deleteReq) {
        levelService.removeByIds(deleteReq.getIds());
        return new BaseRsp<>();
    }

    @ApiOperation("框架列表查询")
    @GetMapping()
    public BaseRsp<List<Level>> getFrames() {
        List<Level> frames = levelService.getFrames();
        return new BaseRsp<>(frames);
    }

    @ApiOperation("指定框架层级数据的全量树形展示")
    @GetMapping("/tree")
    public BaseRsp<Level> get(@ApiParam(name = "resource_id", value = "框架id", required = true) String resource_id) {
        Level frame = levelService.getLevelTree(resource_id);
        return new BaseRsp<>(frame);
    }


    @ApiOperation("新增层级")
    @PostMapping("/level")
    public BaseRsp<Level> addLevel(@RequestBody @Validated LevelAddReq levelAddReq) {
        levelAddReq.setResourceId(null);

        Level level = new Level();
        BeanUtils.copyProperties(levelAddReq, level);

        levelService.addLevel(level);

        return new BaseRsp<>(level);
    }

    @ApiOperation("修改层级")
    @PutMapping("/level")
    public BaseRsp<Level> modifyLevel(@RequestBody @Validated LevelAddReq levelAddReq) {
        Level level = new Level();
        BeanUtils.copyProperties(levelAddReq, level);
        levelService.modifyLevel(level);
        return new BaseRsp<>(level);
    }

    @ApiOperation("删除层级")
    @DeleteMapping("/level")
    public BaseRsp<String> deleteLevel(@RequestBody @Validated DeleteReq deleteReq) {
        levelService.removeByIds(deleteReq.getIds());
        return new BaseRsp<>();
    }

}
