package com.cyberpeace.oj.ojbase.service.context;

import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import lombok.Data;

/**
 * 注意新增上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see NoteUniqueContext
 * @since 1.0.0
 */
@Data
public class NoteAddContext extends NoteUniqueContext {

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 内容
     *
     * @see String
     */
    private String content;

}
