package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("oj_train_manage")
public class TrainManage extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * Frame是Level的一种特殊形式，表示顶级的层级 其parent=null frame=null
     */

    private String name;

    /**
     * 当前层级的父级resourceId 如果是顶级(Frame)则为空
     */
    private String parent;

    /**
     * 当前层级所属的框架(Frame)的resourceId
     */
    private String courseIds;

    /**
     * 当前层级的父级resourceId 如果是顶级(Frame)则为空
     */
    private Integer sort;

}
