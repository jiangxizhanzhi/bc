package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.constant.SelectI18nName;

import lombok.Data;

/**
 * 任务类别缓存
 @author cyberpeace
 @date 2023/05/06
 */
@Data
public class MissionCategoryCache implements SelectI18nName {

	private String resourceId;

	private String cnName;

	private String enName;
}
