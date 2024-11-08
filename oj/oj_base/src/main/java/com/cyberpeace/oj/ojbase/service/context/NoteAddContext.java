package com.cyberpeace.oj.ojbase.service.context;

import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import lombok.Data;

/**
 * 注意新增上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class NoteAddContext extends NoteUniqueContext {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 内容
	 */
	private String content;

}
