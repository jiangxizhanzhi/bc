package com.cyberpeace.oj.ojbase.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.YXUserService;
import com.cyberpeace.integratation.service.bo.YxIndicatorBo;
import com.cyberpeace.oj.ojbase.controller.req.AdminListNoteReq;
import com.cyberpeace.oj.ojbase.controller.req.DeleteResourceReq;
import com.cyberpeace.oj.ojbase.controller.req.EvaluateNoteReq;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteEvaluateMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.service.IndicatorService;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteListBo;
import com.cyberpeace.oj.ojbase.service.context.AdminNoteListQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 管理注意Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminNoteController` 的 Spring MVC 控制器类，用于处理与管理笔记相关的 API 请求。以下是对其主要功能和结构的简要分析：
 * ### 包和导入
 * - 该控制器位于 `com.cyberpeace.oj.ojbase.controller.admin` 包中。
 * - 导入了多个类，包括 FastJSON、MyBatis-Plus 分页插件、自定义业务对象、服务、常量等。
 * ### 注解
 * - `@RestController` 和 `@RequestMapping` 注解用于将该类标记为 RESTful 控制器，并指定其基础路径 `/oj/admin/note`。
 * - `@Autowired` 注解用于自动注入依赖的服务和数据访问对象（DAO）。
 * - `@Api` 注解用于描述控制器的用途，即“后台笔记相关API”。
 * ### 成员变量
 * - `CHILDREN_KEY` 是一个字符串常量，值为 `"children"`，可能用于 JSON 结构中的键名。
 * ### 方法
 * 1. **list**
 * - **功能**：根据请求参数分页查询实验报告。
 * - **请求类型**：GET
 * - **输入**：`AdminListNoteReq` 对象
 * - **输出**：`BaseRsp<ListRsp<NoteListBo>>` 对象
 * 2. **deleteIds**
 * - **功能**：根据给定的资源ID列表批量删除笔记或实验报告。
 * - **请求类型**：DELETE
 * - **输入**：`DeleteResourceReq` 对象
 * - **输出**：`BaseRsp<Boolean>` 对象
 * 3. **evaluateReport**
 * - **功能**：评估实验报告。
 * - **请求类型**：PATCH
 * - **输入**：`EvaluateNoteReq` 对象
 * - **输出**：`BaseRsp<Boolean>` 对象
 * 4. **getIndicator**
 * - **功能**：获取三级标签。
 * - **请求类型**：GET
 * - **输入**：`Integer` 类型的 `type`
 * - **输出**：`BaseRsp<JSONArray>` 对象
 * ### 主要功能
 * - **分页查询**：通过 `list` 方法实现分页查询实验报告的功能。
 * - **批量删除**：通过 `deleteIds` 方法实现批量删除笔记或实验报告的功能。
 * - **评估报告**：通过 `evaluateReport` 方法实现评估实验报告的功能。
 * - **获取指标**：通过 `getIndicator` 方法实现获取三级标签的功能。
 * ### 总结
 * 这个控制器提供了多种与管理笔记相关的功能，包括分页查询、批量删除、评估报告和获取指标。它使用了 MyBatis-Plus 进行数据库操作，并通过 FastJSON 处理 JSON 数据。整体设计清晰，职责明确，便于维护和扩展。
**/
@Api(tags = "oj-后台笔记相关api")
@RestController
@RequestMapping("/oj/admin/note")
//@RequiresAuthentication
public class AdminNoteController {

	/**
	 * 注意Service
	 *
	 * @see NoteService
	 */
	@Autowired
	private NoteService noteService;

	/**
	 * 注意Mapper
	 *
	 * @see NoteMapper
	 */
	@Autowired
	private NoteMapper noteMapper;

	/**
	 * 注意评估Mapper
	 *
	 * @see NoteEvaluateMapper
	 */
	@Autowired
	private NoteEvaluateMapper noteEvaluateMapper;

	@Autowired
	private IndicatorService indicatorService;


	/**
	 * 永信第三方服务数据获取
	 */
	@Autowired
	private YXUserService yxUserService;

	private String CHILDREN_KEY = "children";

	/**
	 * 列表
	 *
	 * @param noteReq 笔记请求体
	 * @return {@code BaseRsp<ListRsp<NoteListBo>> }
	 * @throws
	 * @see AdminListNoteReq
	 * @see BaseRsp<ListRsp<NoteListBo>>
	 */
	@ApiOperation("分页查询实验报告")
	@GetMapping("/list")
	public BaseRsp<ListRsp<NoteListBo>> list(AdminListNoteReq noteReq) {

		Page page = noteReq.toPageParam();
		AdminNoteListQuery noteListQuery = BeanUtils.copy(noteReq, AdminNoteListQuery.class);
		noteListQuery.setUserId(UserInfoContext.getUserContext().getUserId());

		IPage<NoteListBo> noteListBos = noteService.pageByUserNameAndContent(page, noteListQuery,
				CommonConstant.FALSE);
		return new BaseRsp<>(PageUtil.getListResult(noteListBos));
	}

	/**
	 * 删除id列表
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see DeleteResourceReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("笔记/实验报告删除")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteIds(@RequestBody @Validated DeleteResourceReq deleteReq) {

		int row = noteMapper.deleteBatchIds(deleteReq.getResourceIds());
		return new BaseRsp<>(row > 0);
	}

	/**
	 * 评估报告
	 *
	 * @param evaluateNoteReq 评估笔记请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see EvaluateNoteReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("实验报告评估")
	@PatchMapping("/evaluate")
	public BaseRsp<Boolean>
			evaluateReport(@RequestBody @Validated EvaluateNoteReq evaluateNoteReq) {

		NoteEvaluate noteEvaluate = BeanUtils.copy(evaluateNoteReq, NoteEvaluate.class);
		noteEvaluate.setEvaluateTime(new Date());
		noteEvaluate.setEvaluatorId(UserInfoContext.getUserContext().getUserId());

		try {
			noteEvaluateMapper.insert(noteEvaluate);
		} catch (DuplicateKeyException e) {
			noteEvaluateMapper.updateByNote(noteEvaluate);
		}

		return new BaseRsp<>(true);
	}

	/**
	 * 指标
	 *
	 * @param type 类型
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see EvaluateNoteReq
	 * @see EvaluateNoteReq
	 */
	@ApiOperation("获取三级标签")
	@GetMapping("/indicator")
	@Deprecated
	public BaseRsp<JSONArray> getIndicator(@ApiParam(value = "1:攻击 2：防御", name = "type")Integer type) {

		return new BaseRsp<>(new JSONArray());
	}

	@ApiOperation("获取只有三级标签")
	@GetMapping("/indicator_new")
	public BaseRsp<List<YxIndicatorBo>> getIndicatorNews() {
		final List<YxIndicatorBo> yxIndicator = yxUserService.getYxIndicator();

		return new BaseRsp<>(yxIndicator);
	}
}

