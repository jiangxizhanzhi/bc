package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.constant.SelectI18nName;
import lombok.Data;

@Data
public class MissionCategoryCache implements SelectI18nName {

	private String resourceId;

	private String cnName;

	private String enName;
}
