package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/12
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_call_count")
public class SpaceCallCount {

    /**
     * id
     */
	@TableId(type = IdType.AUTO)
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
