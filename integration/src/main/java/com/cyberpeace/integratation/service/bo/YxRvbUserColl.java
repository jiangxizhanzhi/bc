package com.cyberpeace.integratation.service.bo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Getter
@Setter
public class YxRvbUserColl {

	private List<YxUserInfo> commander = Lists.newArrayList();

	private List<YxUserInfo> middle = Lists.newArrayList();

	private List<YxUserInfo> worker = Lists.newArrayList();

}
