package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.constant.SpaceError;
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
import java.util.stream.Collectors;

/**
 * 空间动作帧控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/05
 * @see SpaceActionFrameBaseController
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **功能概述** 
 * 该代码定义了一个名为 `SpaceActionFrameController` 的 Spring MVC 控制器类，用于处理与“空间动作帧”相关的业务逻辑。主要功能包括： 
 * - **新增**：创建新的动作帧。 
 * - **获取框架**：根据资源 ID 获取动作帧框架。 
 * - **保存框架**：保存动作帧框架数据。 
 * - **获取框架类型列表**：获取所有动作帧类型的列表。 
 * - **推荐行动框架**：根据类别 ID 和关键词为模拟推演和能力评估子系统提供推荐的动作帧。 
 * --- 
 * #### 2. **类结构与继承** 
 * - 类 `SpaceActionFrameController` 继承自 `SpaceActionFrameBaseController`，并实现了泛型接口 `<SpaceActionFrameService, SpaceActionFrame, SpaceActionFrameBo, SpaceActionFramePageReq>`。 
 * - 继承基类后，可以复用部分通用逻辑，简化代码实现。 
 * --- 
 * #### 3. **依赖注入** 
 * 通过 `@Autowired` 注入了以下服务类： 
 * - `SpaceActionFrameService`：核心业务逻辑服务。 
 * - `SpaceActionFrameEventTypeService`：事件类型相关服务。 
 * - `SpaceActionFrameTargetTypeService`：目标类型相关服务。 
 * 这些服务类提供了具体的功能实现，例如添加、查询、更新等操作。 
 * --- 
 * #### 4. **方法详细说明** 
 * ##### 4.1 `addAttack` 
 * - **功能**：新增动作帧。 
 * - **逻辑**： 
 * - 如果 `req.resourceId` 为空，则生成一个新的资源 ID。 
 * - 将 `SpaceActionFrameBo` 对象转换为实体对象，并调用 `service.add()` 方法保存到数据库。 
 * - 返回包含新增动作帧的响应对象。 
 * ##### 4.2 `getFrame` 
 * - **功能**：根据资源 ID 获取动作帧框架。 
 * - **逻辑**： 
 * - 调用 `service.getFrame(resource_id)` 获取指定资源 ID 的框架数据。 
 * - 返回包含框架数据的响应对象。 
 * ##### 4.3 `saveFrame` 
 * - **功能**：保存动作帧框架。 
 * - **逻辑**： 
 * - 调用 `req.checkTacticDuplication()` 验证策略是否存在重复。 
 * - 调用 `service.saveFrame(req)` 保存框架数据。 
 * - 返回空响应对象。 
 * ##### 4.4 `getTypes` 
 * - **功能**：获取所有动作帧类型（事件类型和目标类型）的列表。 
 * - **逻辑**： 
 * - 分别调用 `eventTypeService.list()` 和 `targetTypeService.list()` 获取事件类型和目标类型的列表。 
 * - 使用 `StreamApiUtils.map` 将实体对象映射为 `IdAndName` 对象。 
 * - 合并两种类型的列表并返回。 
 * ##### 4.5 `getTuiyanRecFrame` 
 * - **功能**：为模拟推演子系统提供推荐的动作帧。 
 * - **逻辑**： 
 * - 调用 `service.getTuiyanRecFrame(category_id, key_word)` 根据类别 ID 和关键词获取推荐列表。 
 * - 返回包含推荐结果的响应对象。 
 * ##### 4.6 `getNengliRecFrame` 
 * - **功能**：为能力评估子系统提供推荐的动作帧。 
 * - **逻辑**： 
 * - 功能与 `getTuiyanRecFrame` 相同，调用相同的服务方法。 
 * - 返回包含推荐结果的响应对象。 
 * --- 
 * #### 5. **技术特点** 
 * - **Swagger 注解**：使用 `@Api` 和 `@ApiOperation` 描述接口功能，方便文档生成。 
 * - **异常处理**：未显式处理异常，但可以通过全局异常处理器统一管理。 
 * - **流式操作**：利用 `StreamApiUtils.map` 对集合进行高效映射操作。 
 * - **扩展性**：继承基类并依赖注入服务类，便于扩展和维护。 
 * --- 
 * #### 6. **潜在改进点** 
 * - **代码重复**：`getTuiyanRecFrame` 和 `getNengliRecFrame` 方法几乎完全相同，可以合并为一个通用方法。 
 * - **日志记录**：建议在关键逻辑中加入日志记录，便于排查问题。 
 * - **输入校验**：在 `getFrame` 和 `getTuiyanRecFrame` 等方法中增加参数校验逻辑，确保输入合法性。 
 * --- 
 * #### 7. **总结** 
 * 该代码实现了与“空间动作帧”相关的多种业务逻辑，功能全面且结构清晰。通过继承和依赖注入，减少了冗余代码，提高了可维护性。同时，借助 Swagger 注解增强了接口的可读性和易用性。 
**/
@Api(tags = "防御行动框架-行动框架管理")
@RestController
@RequestMapping("/space/action_frame/action_frame")
//@RequiresAuthentication
public class SpaceActionFrameController extends SpaceActionFrameBaseController<SpaceActionFrameService, SpaceActionFrame, SpaceActionFrameBo, SpaceActionFramePageReq> {

    /**
     * Service
     *
     * @see SpaceActionFrameService
     */
    @Autowired
    private SpaceActionFrameService service;

    /**
     * 事件类型Service
     *
     * @see SpaceActionFrameEventTypeService
     */
    @Autowired
    private SpaceActionFrameEventTypeService eventTypeService;

    /**
     * 目标类型Service
     *
     * @see SpaceActionFrameTargetTypeService
     */
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
        SpaceActionFrame frame = baseService.getById(req.getResourceId());
        if ( frame!= null && frame.getBuiltin()) {
            throw new BusinessException(SpaceError.BUILTIN_CANNOT_UPDATE);
        }
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

    @Override
    public void checkValue(SpaceActionFrame entity) {
        if (entity.getBuiltin()) {
            throw new BusinessException(SpaceError.BUILTIN_CANNOT_UPDATE);
        }
        if (!org.apache.commons.lang3.StringUtils.isEmpty(entity.getAnalyze())) {
            CrCommonValidator.validateLength(entity.getAnalyze(), 1, 960,
                    CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
        if (!org.apache.commons.lang3.StringUtils.isEmpty(entity.getDescription())) {
            CrCommonValidator.validateLength(entity.getDescription(), 1, 960,
                    CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }

        if (!org.apache.commons.lang3.StringUtils.isEmpty(entity.getTag())) {
            CrCommonValidator.validateNameLen(entity.getTag(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
    }

    @Override
    protected void checkReference(List<String> ids) {
        List<SpaceActionFrame> actionFrameList = baseService.listByIds(ids);
        List<String> names = actionFrameList.stream().filter(SpaceActionFrame::getBuiltin).map(SpaceActionFrame::getName).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(names)) {
            String name = String.join(",", names);
            throw new BusinessException(SpaceError.BUILTIN_CANNOT_DELETE, name);
        }
    }

}

