package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRuleMapper;
import com.cyberpeace.cr.evaluation.service.EvalRuleService;
import com.cyberpeace.cs.constant.AttachmentPath;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Service
public class EvalRuleServiceImpl extends ServiceImpl<EvalRuleMapper, EvalRule> implements EvalRuleService {

	@Override
	public ListRsp<EvalRule> pageList(EvalRuleListReq req) {
		LambdaQueryWrapper<EvalRule> queryWrapper = new LambdaQueryWrapper<>();
		if (!Objects.isNull(req.getName())){
			queryWrapper.eq(EvalRule::getName, req.getName());
		}
		Page<EvalRule> page = page(req.toPageParam(), queryWrapper);
		return PageUtil.getListResult(page);
	}

	@Override
	public Boolean create(EvalRuleReq req) {
		validate(req);
		isExist(req);
		EvalRule add = BeanUtils.copy(req, EvalRule.class);
		add.setResourceId(IDGeneratorUtil.getID());
		return save(add);
	}

	@Override
	public Boolean update(EvalRuleReq req) {
		validate(req);
		isExist(req);
		EvalRule update = BeanUtils.copy(req, EvalRule.class);
		return updateById(update);
	}

	@Override
	public EvalRule get(String resourceId) {
		return getById(resourceId);
	}

	@Override
	public Boolean delete(List<String> ids) {
		return removeByIds(ids);
	}

	@Override
	public String dumpEvalRule(String roomId) {

		LambdaQueryWrapper<EvalRule> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(EvalRule::getRoomId,roomId);
		List<EvalRule> list = list(queryWrapper);
		JSONObject data = new JSONObject();
		List<JSONObject> userList = Lists.newArrayList();
		for (int i = 0; i < 5; i++) {
			JSONObject userObject = new JSONObject();
			userObject.put("userId", "userId"+i);
			userObject.put("userName", "userName"+i);
			userObject.put("userRole", "userRole"+i);

			userObject.put("score","score"+i);
			userObject.put("mission", "mission"+i);
			userObject.put("radar", "radar"+i);
			userList.add(userObject);
		}

		data.put("user_list", userList);

//		data.put("radarLabels", radarLabels);
		data.put("locale", getTemplateI18n());
		data.put("user_list", userList);
//		data.put("mission_report", getTrainingMissionReport(roomId).getData());
		data.put("report", new TrainingReport());
		data.put("reportTime", "2023-08-01");

		//变量项算法
		String destPdfPath;

		data.put("rule_list", list);
//		data.put("name", list);
		String destHtmlPath = String.format("%s%s-%s.html",
				AttachmentPath.EVAL_RULE_HTML_TEMPLATE.getDesc(), roomId,
				UserInfoContext.getUserContext().getUserId());
		try {
			HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.EVAL_RULE, destHtmlPath);
			destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))+ "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(!new File(destHtmlPath).delete()){
//				log.error("failed to delete file:{}", destHtmlPath);
			}
		}
		return destPdfPath;
	}

	private void validate(EvalRuleReq req) {
		int lenZero = Integer.parseInt(ConfigUtil.getContextProperty("cr.str.len.zero"));
		int lenMin = Integer.parseInt(ConfigUtil.getContextProperty("cr.str.len.min"));
		int nameMax = Integer.parseInt(ConfigUtil.getContextProperty("cr.eval.rule.name.max"));
		int descriptionMax = Integer
				.parseInt(ConfigUtil.getContextProperty("cr.eval.rule.description.max"));
		int scoreMax = Integer.parseInt(ConfigUtil.getContextProperty("cr.eval.rule.score.max"));

		if (req.getName() != null) {
			CrCommonValidator.validateRange(req.getName().length(), lenMin, nameMax,
					CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (StringUtils.isNotEmpty(req.getDescription())) {
			CrCommonValidator.validateRange(req.getDescription().length(), lenZero, descriptionMax,
					CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
		if (null != req.getScore()) {
			CrCommonValidator.validateRange(req.getScore(), 0,scoreMax,CrBusinessErrorCode.ILLEGAL_RULE_SCORE);
		}
	}

	private void isExist(EvalRuleReq req) {
		LambdaQueryChainWrapper<EvalRule> querySet = lambdaQuery()
				.eq(EvalRule::getRoomId, req.getResourceId())
				.eq(EvalRule::getName, req.getName());
		if (req.getResourceId() != null) {
			querySet = querySet.ne(EvalRule::getResourceId, req.getResourceId());
		}
		if (SqlHelper.retBool(querySet.count())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}


	public JSONObject getTemplateI18n() {
		JSONObject jsonObject = new JSONObject();

		for (TrainingReport.TemplateI18n enumData : TrainingReport.TemplateI18n.values()) {
			jsonObject.put(enumData.name(), enumData.getMsg());
		}

		return jsonObject;
	}
}
