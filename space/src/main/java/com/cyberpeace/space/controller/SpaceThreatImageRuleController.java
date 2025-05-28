
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.service.SpaceThreatImageRuleService;
import com.cyberpeace.space.service.context.rule.SpaceThreatImageRuleBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间威胁图像统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-规则库-威胁画像规则")
@RestController
@RequestMapping("/space/rule/space_threat_image_rule")
@RequiresAuthentication
@Slf4j
public class SpaceThreatImageRuleController extends SpaceControllerBase<SpaceThreatImageRuleService, SpaceThreatImageRule, SpaceThreatImageRuleBo, SpacePageReqBase> {

	/**
	 * 检查名称重复
	 *
	 * @param entity 实体
	 * @return boolean
	 * @throws
	 * @see SpaceThreatImageRule
	 * @see boolean
	 */
	@Override
	public boolean checkNameDuplication(SpaceThreatImageRule entity) {
		return true;
	}
}