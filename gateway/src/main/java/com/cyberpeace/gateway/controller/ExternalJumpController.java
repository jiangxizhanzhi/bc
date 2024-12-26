package com.cyberpeace.gateway.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.integratation.service.RemoteXdService;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "外部接口-28所跳转")
@RequestMapping("/gateway/jump")
public class ExternalJumpController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RemoteXdService remoteXdService;

	@ApiOperation("镜像管理")
	@GetMapping("/image_manage")
	public void jumpIM(HttpServletResponse response,
					   @RequestParam(value = "is_same_site", required = false) Boolean isSameSite) {
		setResponse(response, "/page/mg/admin/std/image-manage", isSameSite);
	}

	@ApiOperation("跳转态势页面")
	@GetMapping("/situation")
	public void jumpSituation(HttpServletResponse response,
			@RequestParam(value = "resource_id") String resourceId,
			@RequestParam(value = "hdnm", required = false) String hdnm,
			@RequestParam(value = "pass", required = false) Boolean pass,
			@RequestParam(value = "is_same_site", required = false) Boolean isSameSite) {

		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if (businessTemplate == null) {
			throw new BusinessException(SituationErrorCode.CANNOT_FIND_TOPO);
		}
		// 获取想定状态
		Boolean xdStatus = remoteXdService.getXdStatus(hdnm, resourceId);

		if (Boolean.TRUE.equals(!xdStatus) && !Boolean.TRUE.equals(pass)) {
			// 如果想定未开启，则跳转到空页面
			setResponse(response, "/page/mg/empty-scene-navigation", isSameSite);
		} else {
			// 跳转到态势页面
			setResponse(response,
					"/page/mg/admin/screen-3/visual-screen?name="
							+ getURLEncoderString(businessTemplate.getName()) + "&topo_id="
							+ businessTemplate.getTopoId(),
					isSameSite);
		}
	}

	@ApiOperation("跳转动效控制台页面")
	@GetMapping("/situation_control")
	public void jumpSituationControl(HttpServletResponse response,
									 @RequestParam(value = "resource_id") String resourceId,
									 @RequestParam(value = "type") String type,
									 @RequestParam(value = "is_same_site", required = false) Boolean isSameSite) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if(businessTemplate == null){
			throw new BusinessException(SituationErrorCode.CANNOT_FIND_TOPO);
		}
		setResponse(response,
				"/page/mg/admin/screen-3/visual-screen-control?name=" + getURLEncoderString(businessTemplate.getName()) + "&topo_id=" + businessTemplate.getTopoId() + "&type=" + type, isSameSite);
	}

	@ApiOperation("跳转到后台场景编辑器页面")
	@GetMapping("/scene_edit")
	public void jumpSceneEdit(HttpServletResponse response,
							  @RequestParam(value = "resource_id") String id,
							  @RequestParam(value = "type") String type,
							  @RequestParam(value = "name") String name,
							  @RequestParam(value = "is_same_site", required = false) Boolean isSameSite) {
		setResponse(response, "/page/mg/admin/topo-config?id=" + id + "&type=" + type + "&title=" + getURLEncoderString(name), isSameSite);
	}

	@ApiOperation("跳转到攻防方案管理页面")
	@GetMapping("/scheme_template")
	public void	jumpSchemeTemplate(HttpServletResponse response,
									 @RequestParam(value = "is_same_site", required = false) Boolean isSameSite) {
		setResponse(response, "/page/mg/admin/program-template", isSameSite);
	}

	@ApiOperation("跳转到攻防方案新增页面")
	@GetMapping("/scheme_template_create")
	public void	jumpSchemeTemplateEdit(HttpServletResponse response,
										  @RequestParam(value = "is_same_site", required = false) Boolean isSameSite) {
		setResponse(response, "/page/mg/admin/program-template/create", isSameSite);
	}

	@ApiOperation("跳转到攻防方案编辑页面")
	@GetMapping("/scheme_template_edit")
	public void	jumpSchemeTemplateEdit(HttpServletResponse response,
										  @RequestParam(value = "template_id") String id,
										  @RequestParam(value = "is_same_site", required = false) Boolean isSameSite) {
		setResponse(response, "/page/mg/admin/program-template/edit/" + id, isSameSite);
	}

	@ApiOperation("跳转到实景页面")
	@GetMapping("/realistic_scene")
	public void jumpRealisticScene(HttpServletResponse response,
								   @RequestParam("resource_id") String resourceId,
								   @RequestParam("type") Integer type,
								   @RequestParam(value = "is_same_site", required = false) Boolean isSameSite ) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if (businessTemplate == null) {
			throw new BusinessException(SituationErrorCode.CANNOT_FIND_TOPO);
		}
		if (type.equals(0)) {
			setResponse(response, "/hy/?topo_id=" + businessTemplate.getTopoId() + "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjNmNjU1ZDg3MTEyZjRhZTA5NGJiYWY3MDgzMGUyMGFhIiwiZXhwIjo0ODI0NTI1NzU4LCJrZXkiOiJiNWIyOGE5OWU0NzI0OTQzYWY1NGM0N2VkZGE4YzE5YiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.5U26euvxf0ACAUJSVKZGOQoBeappSgAL0SayOl5Nbks", isSameSite);
		}else if (type.equals(1)) {
			setResponse(response, "/dx/?topo_id=" + businessTemplate.getTopoId() + "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjNmNjU1ZDg3MTEyZjRhZTA5NGJiYWY3MDgzMGUyMGFhIiwiZXhwIjo0ODI0NTI1NzU4LCJrZXkiOiJiNWIyOGE5OWU0NzI0OTQzYWY1NGM0N2VkZGE4YzE5YiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.5U26euvxf0ACAUJSVKZGOQoBeappSgAL0SayOl5Nbks", isSameSite);
		} else if (type.equals(2)) {
			setResponse(response, "/dt/?topo_id=" + businessTemplate.getTopoId() + "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjNmNjU1ZDg3MTEyZjRhZTA5NGJiYWY3MDgzMGUyMGFhIiwiZXhwIjo0ODI0NTI1NzU4LCJrZXkiOiJiNWIyOGE5OWU0NzI0OTQzYWY1NGM0N2VkZGE4YzE5YiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.5U26euvxf0ACAUJSVKZGOQoBeappSgAL0SayOl5Nbks", isSameSite);
		}
	}

	/**
	 * 设置重定向返回体
	 */
	private void setResponse(HttpServletResponse response, String header, Boolean isSameSite) {

		// 构造返回体
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Cache-Control", "no-cache");
		String mpToken = SystemPropUtil.getProp("ADMIN_TOKEN");
		if (mpToken == null) {
			mpToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
					".eyJpZCI6IjNmNjU1ZDg3MTEyZjRhZTA5NGJiYWY3MDgzMGUyMGFhIiwiZXhwIjo0ODI0NTI1NzU4LCJrZXkiOiJiNWIyOGE5OWU0NzI0OTQzYWY1NGM0N2VkZGE4YzE5YiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.5U26euvxf0ACAUJSVKZGOQoBeappSgAL0SayOl5Nbks";
		}
		if(isSameSite!=null && isSameSite){
			response.setHeader("Set-Cookie", "cr_jwttoken=" + mpToken + "; SameSite=None; Secure; Path=/");
		}else {
			response.setHeader("Set-Cookie", "cr_jwttoken=" + mpToken + "; Path=/");
		}
		response.setHeader("Location", header);

	}


	private String getURLEncoderString(String str){
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
