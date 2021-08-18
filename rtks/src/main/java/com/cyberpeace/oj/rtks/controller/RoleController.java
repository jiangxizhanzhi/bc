package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.RoleAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "TRKS-岗位管理")
@RestController
@RequestMapping("/rtks/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("新增岗位")
    @PostMapping()
    public BaseRsp<Role> addRole(@RequestBody @Validated RoleAddReq roleAddReq) {
        Role role = new Role();
        BeanUtils.copyProperties(roleAddReq, role);
        roleService.save(role);
        return new BaseRsp<>(role);
    }

    @ApiOperation("修改岗位")
    @PutMapping()
    public BaseRsp<Role> modifyTask(@RequestBody @Validated RoleAddReq roleAddReq) {
        Role role = new Role();
        BeanUtils.copyProperties(roleAddReq, role);
        roleService.updateById(role);
        return new BaseRsp<>(role);
    }

    @ApiOperation("删除岗位")
    @DeleteMapping()
    public BaseRsp<Role> deleteFrame(@RequestBody @Validated DeleteReq deleteReq) {
        roleService.removeByIds(deleteReq.getIds());
        return new BaseRsp<>();
    }
}
