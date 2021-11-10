package com.cyberpeace.oj.practice.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.PracticeMixinController;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryListReq;
import com.cyberpeace.oj.practice.controller.req.PracticeSetUpdateReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "习题集")
@RestController
@RequestMapping("/oj/practice-set")
@RequiresAuthentication
public class PracticeSetController extends PracticeMixinController<PracticeSet> {

    @Autowired
    PracticeSetMapper practiceSetMapper;

    @ApiOperation("获取单个习题集")
    @GetMapping()
    public BaseRsp<PracticeSet> get(@RequestParam("resource_id") @NotBlank String resourceId) {
        PracticeSet result = practiceSetMapper.selectById(resourceId);
        if (result == null) {
            throw new ResourceNotFoundException(resourceId);
        }
        return new BaseRsp<>(result);
    }

    @ApiOperation("获取习题集")
    @GetMapping("/list")
    public BaseRsp<ListRsp<PracticeSet>> getList(PracticeCategoryListReq queryParams) {
        IPage<PracticeSet> result = practiceSetMapper.getList(queryParams.toPageParam(), queryParams.getPracticeType(), queryParams.getPattern());
        return new BaseRsp<>(PageUtil.getListResult(result));
    }

    @ApiOperation("创建习题集")
    @PostMapping()
    public BaseRsp<PracticeSet> create(@RequestBody PracticeSetUpdateReq requestData) {
        PracticeSet object = new PracticeSet();
        object.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        BeanUtils.copyProperties(requestData, object, "authOrganization", "shareTeachers");
        practiceSetMapper.insert(object);
        return new BaseRsp<>(object);
    }

    @ApiOperation("部分更新习题集")
    @PatchMapping()
    public BaseRsp<PracticeSet> partialUpdate(@RequestBody PracticeSetUpdateReq requestData) {
        PracticeSet object = new PracticeSet();
        BeanUtils.copyProperties(requestData, object, "authOrganization", "shareTeachers");
        practiceSetMapper.updateById(object);

        return new BaseRsp<>(practiceSetMapper.selectById(requestData.getResourceId()));
    }

    @ApiOperation("批量删除习题集")
    @DeleteMapping()
    public BaseRsp<Integer> batchDelete(@RequestBody ResourceDelReq requestData) {
        return new BaseRsp<>(practiceSetMapper.batchDelete(requestData.getResourceIds()));
    }

}
