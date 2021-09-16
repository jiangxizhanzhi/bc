package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.dao.constants.Auth;
import com.cyberpeace.oj.practice.dao.constants.Share;
import com.cyberpeace.oj.practice.dao.constants.Status;
import com.cyberpeace.oj.practice.dao.constants.TestPaperType;
import lombok.Data;

@Data
@TableName("practice_test_paper")
public class TestPaper extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private String description;

    private String logo;

    private Integer practiceCount;

    private Float totalScore;

    @TableField("public")
    private Boolean isPublic;

    private String createUser;

    /**
     * 试卷类型
     */
    private Integer type = TestPaperType.LIB.getValue();

    private Integer status = Status.NORMAL.getValue();


    /**
     * 授权访问
     */
    private Integer auth = Auth.CUSTOM.getValue();

    /**
     * 授权操作
     */
    private Integer share = Share.CUSTOM.getValue();

    /**
     * 已授权访问组织
     */
    private String authOrganization = "[]";

    /**
     * 已授权访问用户
     */
    private String shareTeachers = "[]";
}
