package com.cyberpeace.cr.hld.service.context;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

public class HldBaseInfo extends BaseInfo {

	@Override
	public void validate() {

		String name = this.getName();
		String description = this.getDescription();

		if (StringUtils.isNotEmpty(name)) {
			CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}

		if (StringUtils.isNotEmpty(description)) {
			CrCommonValidator.validateDescLen(this.getDescription(),
					CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}

	}

}
