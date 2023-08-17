package com.cyberpeace.bs.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 行为管理相关接口
 *
 * @author SN
 * @date 2023/05/06
 */
@Api(tags = "行为模拟-行为管理")
@RestController
@RequestMapping("/bs/behavior")
// @RequiresAuthentication
public class BehaviorController {

    @Autowired
    LcService lcService;

    /**
     * 行为分页列表接口
     *
     * @param pageReq 分页请求
     * @return {@code BaseRsp<ListRsp<LcBehavior>>}
     */
    @ApiOperation("获取行为列表")
    @GetMapping("/page")
    public BaseRsp<ListRsp<LcBehavior>> pageBehavior(BehaviorPageReq pageReq) {
        ListRsp<LcBehavior> behaviorListRsp = lcService.getLcBehaviorList(pageReq);
        return new BaseRsp<>(behaviorListRsp);
    }

    /**
     * 获取行为变量接口
     *
     * @param id id
     * @return {@code BaseRsp<VariableListBo>}
     */
    @ApiOperation("获取变量列表")
    @GetMapping("/variable")
    public BaseRsp<VariableListBo> getVariables(@ApiParam(value = "行为id", name = "id") String id) {
        VariableListBo res = lcService.getVariableList(id);
        return new BaseRsp(res);
    }

    /**
     * 更新变量接口
     *
     * @param req 请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("编辑行为变量")
    @PutMapping("/variable")
    public BaseRsp updateVariables(@Validated @RequestBody VariableModifyReq req) {
        lcService.updateVariable(req);
        return new BaseRsp();
    }

    /**
     * 删除行为接口
     *
     * @param req 要求事情
     * @return {@code BaseRsp<Boolean>}
     */
    @ApiOperation("批量删除行为接口")
    @DeleteMapping("/")
    public BaseRsp<Boolean> deleteBehavior(@Validated @RequestBody IdListReq req) {
        Boolean rsp = lcService.deleteBehavior(req.getIds());
        return new BaseRsp<>(rsp);
    }

    /**
     * 将行为导出为zip文件
     * http响应返回一个文件流
     *
     * @param response http响应
     * @param req      请求体
     */
    @ApiOperation("导出行为")
    @PostMapping("/export")
    public void exportFile(HttpServletResponse response, @Validated @RequestBody IdListReq req) {
        lcService.exportBehavior(response, req.getIds());
    }

    /**
     * 导入行为接口
     * 接受文件流输入，将zip文件包含的行为导入系统
     *
     * @param file 文件
     * @return {@code BaseRsp<String>}
     */
    @ApiOperation("导入行为")
    @PutMapping("/import")
    public BaseRsp<String> importBehavior(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPTY_FILE);
        }
        String rsp = lcService.importBehavior(file);
        return new BaseRsp<>(rsp);
    }

	/**
	 * excel导入行为接口
	 * 接受文件流输入，将excel文件包含的行为导入系统
	 *
	 * @param file excel文件
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("导入脚本行为")
	@PutMapping("/import/script")
	public BaseRsp<String> importBehaviorByExcel(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BusinessException(ErrorCode.EMPTY_FILE);
		}
		if (!file.getOriginalFilename().endsWith(BsConstants.SCRIPT_SUFFIX)) {
			throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
		}
		String rsp = lcService.importBehaviorByExcel(file);
		return new BaseRsp<>(rsp);
	}

    /**
     * 获取行为录制跳转host
     * 用于前端页面跳转
     *
     * @return {@code BaseRsp<String>}
     */
    @ApiOperation("获取行为录制跳转host")
    @GetMapping("/host")
    public BaseRsp<String> getLcHost() {
        String host = lcService.getLcHost();
        return new BaseRsp<>(host);
    }

}
