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
		StringBuilder str = new StringBuilder();
		int i = 1;
		for (RoleBo stage : roleBos) {
			// 检查整数溢出
			if (i == Integer.MAX_VALUE) {
				throw new IllegalStateException("Stage count exceeds maximum integer value");
			}

			str.append("## 阶段").append(i++).append(System.lineSeparator())
					.append(stage.getTeamName()).append(System.lineSeparator())
					.append("### 任务").append(System.lineSeparator());

			for (String task : stage.getRoleName()) {
				str.append("- ").append(task).append(System.lineSeparator());
			}
		}
		return LocalStringUtil.frontBackLine(str.toString());
	}
}
