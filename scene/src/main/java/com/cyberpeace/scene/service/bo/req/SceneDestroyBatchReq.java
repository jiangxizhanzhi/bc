package com.cyberpeace.scene.service.bo.req;

import java.util.List;

import com.cyberpeace.common.model.DeleteReq;

import lombok.Data;

@Data
public class SceneDestroyBatchReq extends DeleteReq {

	private List<String> targetNames;
}
