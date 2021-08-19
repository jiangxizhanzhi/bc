package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("course_direction")
public class Direction extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private Integer status;

    private Integer eBook;

}
