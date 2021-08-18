package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.controller.req.RoleAddReq;
import com.cyberpeace.oj.rtks.controller.req.RoleRelatedAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.entity.RoleRelated;
import com.cyberpeace.oj.rtks.service.RoleRelatedService;
import com.cyberpeace.oj.rtks.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "TRKS-岗位管理")
@RestController
@RequestMapping("/rtks/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRelatedService roleRelatedService;


    @ApiOperation("新增岗位")
    @PostMapping()
    public BaseRsp<Role> addRole(@RequestBody @Validated RoleAddReq roleAddReq) {
        Role role = roleService.addRole(roleAddReq);
        return new BaseRsp<>(role);
    }

    @ApiOperation("修改岗位")
    @PutMapping()
    public BaseRsp<Role> modifyTask(@RequestBody @Validated RoleAddReq roleAddReq) {
        Role role = roleService.modifyRole(roleAddReq);
        return new BaseRsp<>(role);
    }

    @ApiOperation("删除岗位")
    @DeleteMapping()
    public BaseRsp<Role> deleteFrame(@RequestBody @Validated DeleteReq deleteReq) {
        roleService.removeByIds(deleteReq.getIds());
        return new BaseRsp<>();
    }

    @ApiOperation("根据岗位名的模糊列表查询")
    @GetMapping()
    public BaseRsp<ListRsp<Role>> searchRoles(ListReq<Role> listReq) {
        ListRsp<Role> data = roleService.searchRolesByName(listReq);
        return new BaseRsp<>(data);
    }

    @ApiOperation("添加岗位职责/知识点/职责/能力清单")
    @PostMapping("/related")
    public BaseRsp<String> addRoleRelated(@RequestBody @Validated RoleRelatedAddReq roleRelatedAddReq) {
        roleRelatedService.addRoleRelated(roleRelatedAddReq.getRole_id(), roleRelatedAddReq.getItems());
        return new BaseRsp<>();
    }

    @ApiOperation("批量删除岗位职责/知识点/职责/能力清单")
    @DeleteMapping("/related")
    public BaseRsp<String> batchDeleteRoleRelated(@RequestBody @Validated DeleteReq deleteReq) {
        roleRelatedService.removeByIds(deleteReq.getIds());
        return new BaseRsp<>();
    }

    @ApiOperation("获取删除岗位职责/知识点/职责/能力清单")
    @GetMapping("/related")
    public BaseRsp<Map<Integer, List<RoleRelated>>> getRoleRelated(String roleId) {
        return new BaseRsp<>(roleRelatedService.getByRoleId(roleId));
    }


    // todo
    //编排岗位基础课
    //编排岗位专业课
    //返回岗位所有的T数据
    //返回岗位所有的K数据
}
