package com.cyberpeace.cr.crbase.dao.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.constant.BaseI18n;

import lombok.Data;
import lombok.Getter;

@TableName(value = "cr_notice")
@Data
public class CrNotice extends BaseTable {

    /**
    * uuid
    */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;
    /**
    * 创建人id
    */
    private String createUserId;
    /**
    * 公告内容
    */
    private String context;
    /**
    * 公告对象
    */
    private Integer role;
    /**
    * 房间id
    */
    private String roomId;

    /**
     * 是否置顶0否1是
     */
    private Integer isTop;

    private String noticeUsers;

    public boolean reoveUserId(String userId) {
        List<String> userIds = JsonUtil.toJsonObjectArray(noticeUsers, String.class);
        boolean remove = userIds.remove(userId);
        if (remove) {
            noticeUsers = JsonUtil.toJsonString(userIds);
            return true;
        }
        return false;
    }

    public enum RoleGroup implements GenericEnum, com.cyberpeace.common.enums.RoleGroup, BaseI18n {

        /***/
        TEACHER(1, "全部", "all"),
        STUDENT(2, "学员", "Student"),
        RED(3, "红方", "Red"),
        BLUE(4, "蓝方", "Blue");

        @Getter
        private final Integer code;

        @Getter
        private final String chinese;

        @Getter
        private final String english;

        RoleGroup(Integer code, String chinese, String english) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
        }

        @Override
        public String getChinese() {
            return this.chinese;
        }

        @Override
        public String getEnglish() {
            return this.english;
        }

        @Override
        public String getDesc() {
            return this.getMsg();
        }
    }

}
