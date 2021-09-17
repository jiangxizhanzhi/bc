package com.cyberpeace.evaluate.controller.res;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestInfoRsp implements Serializable {

	private String infoId;

	private String name;

	private String desc;;

	private String logo;;

	private String schemeId;
}
