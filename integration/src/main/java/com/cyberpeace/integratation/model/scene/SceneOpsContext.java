package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.google.common.collect.Maps;

import lombok.Data;

/**
 * 操作场景实例的上下文。
 * 部分场景相关接口需要传入该数据
 */
@Data
public class SceneOpsContext implements Serializable {

	private String accessUserId = UserInfoContext.getUserContext().getUserId();

	private Map<String, String> context = Maps.newHashMap();
}
