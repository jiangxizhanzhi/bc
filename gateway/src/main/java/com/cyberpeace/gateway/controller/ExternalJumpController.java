package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@Api(tags = "外部接口-28所跳转")
@RequestMapping("/gateway/jump")
public class ExternalJumpController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@ApiOperation("镜像管理")
	@GetMapping("/image_manage")
	public void jumpIM(HttpServletResponse response) {
		setResponse(response, "/page/mg/admin/std/image-manage");
	}

	@ApiOperation("跳转态势页面")
	@GetMapping("/situation")
	public void jumpSituation(HttpServletResponse response,
							  @RequestParam(value = "resource_id") String resourceId) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		setResponse(response,
				"/page/mg/admin/screen-3/visual-screen?name=" + getURLEncoderString(businessTemplate.getName()) + "&topo_id=" + businessTemplate.getTopoId());
	}

	@ApiOperation("跳转动效控制台页面")
	@GetMapping("/situation_control")
	public void jumpSituationControl(HttpServletResponse response,
									 @RequestParam(value = "resource_id") String resourceId) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		setResponse(response,
				"/page/mg/admin/screen-3/visual-screen-control?name=" + getURLEncoderString(businessTemplate.getName()) + "&topo_id=" + businessTemplate.getTopoId());
	}

	@ApiOperation("跳转到后台场景编辑器页面")
	@GetMapping("/scene_edit")
	public void jumpSceneEdit(HttpServletResponse response,
							  @RequestParam(value = "resource_id") String id,
							  @RequestParam(value = "type") String type,
							  @RequestParam(value = "name") String name) {
		setResponse(response, "/page/mg/admin/topo-config?id=" + id + "&type=" + type + "&title=" + getURLEncoderString(name));
	}

	/**
	 * 设置重定向返回体
	 */
	private void setResponse(HttpServletResponse response, String header) {

		// 构造返回体
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Location", header);
		String mpToken = SystemPropUtil.getProp("ADMIN_TOKEN");
		if (mpToken == null) {
			mpToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
					".eyJpZCI6IjNmNjU1ZDg3MTEyZjRhZTA5NGJiYWY3MDgzMGUyMGFhIiwiZXhwIjo0ODI0NTI1NzU4LCJrZXkiOiJiNWIyOGE5OWU0NzI0OTQzYWY1NGM0N2VkZGE4YzE5YiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.5U26euvxf0ACAUJSVKZGOQoBeappSgAL0SayOl5Nbks";
		}
		response.setHeader("Set-Cookie", "cr_jwttoken=" + mpToken + "; SameSite=Lax; Path=/");

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
