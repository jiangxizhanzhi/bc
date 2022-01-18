package com.cyberpeace.cr.report_score.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("cr_report_score")
public class ReportScore extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String reportName;

    private String reportDescription;

    private Integer subject;

    private Integer anonymous;

    private Date submitTime;

    private Integer status;

    private String reportFile;

    private Integer score;

    private String comment;

    private Date scoreTime;

    private String teamId;

    private String teamName;

    private String raterId;

    private String raterName;

    private enum SubjectCode {
        INVESTIGATION(1, "网络侦查"),
        ANALYSIS(2,"抵近分析"),
        STORM(3, "外网强攻"),
        AMBUSH(4, "内网设伏"),
        ATTACK(5, "内网攻击");

        @Getter
        private Integer code;

        @Getter String desc;

        private SubjectCode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    private enum StatusCode {
        REJECT(1, "驳回"),
        PASS(2, "通过"),
        APPROVING(3, "待审核"),
        EDITING(4, "编辑中");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        private StatusCode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
