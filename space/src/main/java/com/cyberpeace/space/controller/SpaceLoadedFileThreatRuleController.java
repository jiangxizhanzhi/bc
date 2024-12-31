
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleBo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.service.SpaceLoadedFileThreatRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Api(tags = "知识库-规则库-负载文件威胁检测规则")
@RestController
@RequestMapping("/space/rule/space_loaded_file_threat_rule")
@RequiresAuthentication
@Slf4j
public class SpaceLoadedFileThreatRuleController extends SpaceControllerBase<SpaceLoadedFileThreatRuleService, SpaceLoadedFileThreatRule, SpaceLoadedFileThreatRuleBo> {

    @Override
    @ApiOperation("列表")
    @GetMapping("/list")
    public BaseRsp<ListRsp<SpaceLoadedFileThreatRuleBo>> page(PageReqBase req) {
        LambdaQueryWrapper<SpaceLoadedFileThreatRule> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(req.getPattern())) {
            wrapper.like(SpaceLoadedFileThreatRule::getName, req.getPattern());
        }
        wrapper.orderByDesc(SpaceLoadedFileThreatRule::getExecuteType, SpaceLoadedFileThreatRule::getUpdateTime);
        IPage<SpaceLoadedFileThreatRule> iPage = baseService.page(req.toPageParam(), wrapper);
        Map<String, IdAndName> relatedMap = getRelatedMap(iPage.getRecords());
        iPage.convert(e -> e.entity2Bo(relatedMap));
        ListRsp<SpaceLoadedFileThreatRuleBo> res = PageUtil.getListResult(iPage);
        return new BaseRsp<>(res);
    }

}