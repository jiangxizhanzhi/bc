package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("cr_notice")
public class CrNotice extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 训练业务id
     */
    private String businessId;

    /**
     * 训练类型
     */
    private Integer businessType;

    /**
     * 内容
     */
    private String content;

    /**
     * 公告对象
     */
    private Integer group;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 附件id
     */
    private String attachmentId;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 创建用户id
     */
    private String createUserId;

    public enum Group {
        ALL(1, "全局"),
        RED(2, "红队"),
        BLUE(3, "蓝队");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Group(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Status {
        NORMAL(1, "正常"),
        TOP(2, "置顶");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
