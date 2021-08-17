package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.AbilityAddReq;
import com.cyberpeace.oj.rtks.controller.req.AbilityListReq;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.dao.entity.Ability;
import com.cyberpeace.oj.rtks.service.AbilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RTKS-能力库管理")
@RestController
@RequestMapping("/rtks")
public class AbilityController {

    @Autowired
    private AbilityService abilityService;

    @ApiOperation("查询单条能力")
    @GetMapping("/ability")
    public BaseRsp<Ability> getAbility(
            @ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
        Ability ability = abilityService.getById(resource_id);

        return new BaseRsp(ability);
    }

    @ApiOperation("新增单条能力")
    @PostMapping("/ability")
    public BaseRsp<String> addAbility(@RequestBody @Validated AbilityAddReq abilityAddReq) {
        Ability toAddAbility = new Ability();
        BeanUtils.copyProperties(abilityAddReq, toAddAbility);

        abilityService.save(toAddAbility);

        return new BaseRsp<>();
    }

    @ApiOperation("修改单条能力")
    @PutMapping("/ability")
    public BaseRsp<String> modifyAbility(@RequestBody @Validated AbilityAddReq abilityAddReq) {
        Ability toUpdateAbility = new Ability();
        BeanUtils.copyProperties(abilityAddReq, toUpdateAbility);

        abilityService.updateById(toUpdateAbility);

        return new BaseRsp<>();
    }

    @ApiOperation("删除能力")
    @DeleteMapping("/ability")
    public BaseRsp<String> deleteAbility(@RequestBody @Validated DeleteReq deleteReq) {
        abilityService.removeByIds(deleteReq.getIds());

        return new BaseRsp<>();
    }

    @ApiOperation("分页查询")
    @GetMapping("/ability/list")
    public BaseRsp<List<Ability>> list(@Validated AbilityListReq abilityListReq) {
        ListRsp<Ability> data = abilityService.page(abilityListReq);

        return new BaseRsp(data);
    }

}
