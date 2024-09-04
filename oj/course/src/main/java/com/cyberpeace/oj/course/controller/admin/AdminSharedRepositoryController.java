package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.teamtrain.controller.req.ResourceIdReq;
import com.cyberpeace.cs.service.context.IdListReq;
import com.cyberpeace.oj.course.service.SharedRepositoryService;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-后台共享内容库api")
@RestController
@RequestMapping("/oj/admin/lesson/repository")
@RequiresAuthentication
@Slf4j
public class AdminSharedRepositoryController {

	@Autowired
	private SharedRepositoryService sharedRepositoryService;

	@ApiOperation("获取共享内容库")
	@GetMapping("/shared")
	public BaseRsp<ListRsp<AdminLessonListBo>> getSharedRepository(PageReqBase req,
			@ApiParam(value = "用户名称", name = "user") String user) {
		ListRsp<AdminLessonListBo> res = sharedRepositoryService.getSharedRepository(req, user);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取我的内容")
	@GetMapping("/my")
	public BaseRsp<ListRsp<AdminLessonListBo>> getMyRepository(PageReqBase req) {
		ListRsp<AdminLessonListBo> res = sharedRepositoryService.getMyRepository(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("保存到我的内容")
	@PostMapping("/add_to_my")
	public BaseRsp add2My(@RequestBody ResourceIdReq req) {
		sharedRepositoryService.add2My(req.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("共享内容")
	@PostMapping("/share")
	public BaseRsp shareMy(@RequestBody IdListReq req) {
		sharedRepositoryService.share(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("私有内容")
	@PostMapping("/privatize")
	public BaseRsp privatizeMy(@RequestBody IdListReq req) {
		sharedRepositoryService.privatize(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("删除内容")
	@DeleteMapping("/")
	public BaseRsp deleteMy(@RequestBody IdListReq req) {
		sharedRepositoryService.delete(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("获取编排列表")
	@GetMapping("/list")
	public BaseRsp<List<AdminLessonListBo>> getListAll() {
		List<AdminLessonListBo> res = sharedRepositoryService.getAllMy();
		return new BaseRsp<>(res);
	}

	@ApiOperation("保存编排")
	@PostMapping("/arrange")
	public BaseRsp saveArrangement(@RequestBody IdListReq req) {
		sharedRepositoryService.saveArrangement(req.getIds());
		return new BaseRsp<>();
	}

}
