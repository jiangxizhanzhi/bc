package com.cyberpeace.cr.crbase.service.context.paraser;


import java.util.List;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cs.service.context.RoleBo;

/**
 * 任务解析
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class TaskParser implements MdContentParser<List<RoleBo>, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code List<RoleBo> }
	 */
	@Override
	public List<RoleBo> parse(Object content) {
		return null;
	}

	/**
	 * 写入
	 *
	 * @param roleBos 岗位BOS
	 * @return {@code String }
	 */
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
		return LocalStringUtil.frontBackLine(str);
	}
}
