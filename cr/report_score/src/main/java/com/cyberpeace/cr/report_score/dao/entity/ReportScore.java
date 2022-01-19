package com.cyberpeace.cr.report_score.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("cr_report_score")
public class ReportScore extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**报告名称 */
    private String reportName;

    /**报告简介 */
    private String reportDescription;

    /**所属科目 */
    private Integer subject;

    /**是否匿名 */
    private Integer anonymous;

    /**提交时间 */
    private Date submitTime;

    /**状态 */
    private Integer status;

    /**报告名称 */
    private String reportFile;

    /**得分 */
    private Integer score;

    /**报告评语 */
    private String comment;

    /**评分时间 */
    private Date scoreTime;

    /**评分人id */
    private String raterId;

    /**评分人姓名 */
    private String raterName;

    public enum SubjectCode implements GenericEnum {
        INVESTIGATION(1, "网络侦查"),
        ANALYSIS(2,"抵近分析"),
        STORM(3, "外网强攻"),
        AMBUSH(4, "内网设伏"),
        ATTACK(5, "内网攻击");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        SubjectCode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum StatusCode implements GenericEnum {
        REJECT(1, "驳回"),
        PASS(2, "通过"),
        APPROVING(3, "待审核"),
        EDITING(4, "编辑中");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        StatusCode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
