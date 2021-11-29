package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationReferenceData implements Serializable {

	private Integer sceneType;

	private String createUser;

	private Boolean isWeb;

	private String sceneName;

	private String appResourceId;

	private String content;

}
