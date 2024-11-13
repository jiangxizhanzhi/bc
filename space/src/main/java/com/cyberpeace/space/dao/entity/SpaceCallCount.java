package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 调用计数
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
@TableName("space_call_count")
public class SpaceCallCount {

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 调用
     */
    private Integer calls;

}
