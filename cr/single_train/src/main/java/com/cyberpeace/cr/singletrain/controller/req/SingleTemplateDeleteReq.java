package com.cyberpeace.cr.singletrain.controller.req;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SingleTemplateDeleteReq {

	@NotEmpty
	private List<@NotBlank String> ids;

	/** 用户在删除确认框中确认关闭的房间。 */
	@NotNull
	private List<@NotBlank String> roomIds = new ArrayList<>();
}
