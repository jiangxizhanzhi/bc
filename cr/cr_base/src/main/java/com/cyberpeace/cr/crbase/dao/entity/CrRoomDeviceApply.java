package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Data
public class CrRoomDeviceApply extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String roomId;

    private String roomName;

    private String deviceId;

    private String sceneDeviceId;

    private Integer status;

    private String applyUserId;

    private String applyRoleId;

    private Date applyTime;

    private String allowUserId;

    private Date allowTime;

    private Integer businessType;

    /**
     * 单兵训练使用
     */
    private String businessTemplateId;

    public enum Status {
        APPLYING(1, "申请中"),
        AGREE(2, "同意"),
        REFUSE(3, "拒绝"),
        RECYCLED(4, "已回收");

        public static List<Integer> APPLIED_LIST = Lists.newArrayList(APPLYING.getCode(), AGREE.getCode());

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    public Boolean isSingle() {
        return businessType.equals(BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
    }

}
