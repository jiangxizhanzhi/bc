package com.cyberpeace.cs.service.context.paraser;


import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cs.service.context.RoleBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskParser implements MdContentParser<List<RoleBo>, String> {

	@Override
	public List<RoleBo> parse(Object content) {
		return null;
	}

	@Override
	public String write(List<RoleBo> roleBos) {
		String str = "";
		int i = 1;
		for (RoleBo stage : roleBos) {
			str = str + "## 阶段" + i++ + System.lineSeparator();
			str = str + stage.getTeamName() + System.lineSeparator();
			str = str + "### 任务" + System.lineSeparator();
			for(String task : stage.getRoleName()){
				str = str + "- " + task + System.lineSeparator();
			}
		}
		if (StringUtils.isBlank(str)) {
			str = CommonConstant.EMPTY_STRING;
		}
		return System.lineSeparator() + str;
	}
}
