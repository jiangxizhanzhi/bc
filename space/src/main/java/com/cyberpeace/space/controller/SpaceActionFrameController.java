package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameEventTypeService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionFrameTargetTypeService;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFramePageReq;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameRecBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 空间动作帧控制器
 *
 * @author SN
 * @date 2024/11/05
 */
@Api(tags = "防御行动框架-行动框架管理")
@RestController
@RequestMapping("/space/action_frame/action_frame")
//@RequiresAuthentication
public class SpaceActionFrameController extends SpaceActionFrameBaseController<SpaceActionFrameService, SpaceActionFrame, SpaceActionFrameBo, SpaceActionFramePageReq> {

    /**
     * Service
     */
    @Autowired
    private SpaceActionFrameService service;

    @Autowired
    private SpaceActionFrameEventTypeService eventTypeService;

    @Autowired
    private SpaceActionFrameTargetTypeService targetTypeService;

    /**
     * 重写新增
     */
    @ApiOperation("新增")
    @PostMapping("")
    @Override
    public BaseRsp<SpaceActionFrame> addAttack(@RequestBody SpaceActionFrameBo req) {

        // 新增
        if (StringUtils.isBlank(req.getResourceId())) {
            req.setResourceId(IDGeneratorUtil.getID());
        }
        SpaceActionFrame entity = req.bo2Entity();
        service.add(entity);

        return new BaseRsp<>(entity);
    }

    /**
     * 获取框架
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<SpaceActionFrameLevel> }
     */
    @ApiOperation("获取框架")
    @GetMapping("/frame")
    public BaseRsp<ActionFrameArrangeBo>
    getFrame(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
        ActionFrameArrangeBo res = service.getFrame(resource_id);
        return new BaseRsp<>(res);
    }

    /**
     * 保存帧
     *
     * @param req 请求体
     * @return {@code BaseRsp }
     */
    @ApiOperation("保存框架")
    @PostMapping("/frame")
    public BaseRsp saveFrame(@RequestBody ActionFrameArrangeBo req) {
        req.checkTacticDuplication();
        service.saveFrame(req);
        return new BaseRsp<>();
    }

    /**
     * 获取全部框架类型
     */
    @ApiOperation("框架威胁类型列表")
    @GetMapping("/type_list")
    public BaseRsp<List<IdAndName>> getTypes() {
        List<IdAndName> targetTypeList = StreamApiUtils.map(targetTypeService.list(),
                e -> new IdAndName(e.getResourceId(), e.getName()));
        List<IdAndName> eventTypeList = StreamApiUtils.map(eventTypeService.list(),
                e -> new IdAndName(e.getResourceId(), e.getName()));
        targetTypeList.addAll(eventTypeList);
        return new BaseRsp<>(targetTypeList);
    }

    /**
     * 模拟推演子系统获取推荐行动框架
     */
    @ApiOperation("模拟推演子系统获取推荐行动框架")
    @GetMapping("/tuiyan")
    public BaseRsp<List<SpaceActionFrameRecBo>> getTuiyanRecFrame(
            @ApiParam(value = "类型id", name = "category_id") String category_id,
            @ApiParam(value = "关键字", name = "key_word") String key_word) {
        List<SpaceActionFrameRecBo> res = service.getTuiyanRecFrame(category_id, key_word);
        return new BaseRsp<>(res);
    }

    /**
     * 能力评估子系统获取推荐行动框架
     */
    @ApiOperation("能力评估子系统获取推荐行动框架")
    @GetMapping("/nengli")
    public BaseRsp<List<SpaceActionFrameRecBo>> getNengliRecFrame(
            @ApiParam(value = "类型id", name = "category_id") String category_id,
            @ApiParam(value = "关键字", name = "key_word") String key_word) {
        List<SpaceActionFrameRecBo> res = service.getTuiyanRecFrame(category_id, key_word);
        return new BaseRsp<>(res);
    }

}
