package com.cyberpeace.oj.ojbase.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "oj-前台资源收藏相关api")
@RestController
@RequestMapping("/oj/collection")
@RequiresAuthentication
public class WebResourceCollectionController {

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@ApiOperation("资源收藏或取消收藏")
	@PostMapping("")
	public BaseRsp<Boolean> add(@RequestBody @Valid CollectionReq collectionReq) {

		CollectionContext collectionContext = BeanUtils.copy(collectionReq,
				CollectionContext.class);
		collectionContext.setUserId(collectionContext.getUserId());

		Boolean result = resourceCollectionService.collectionResource(collectionContext,
				collectionReq.getIsCollection());

		return new BaseRsp<>(result);
	}

}
