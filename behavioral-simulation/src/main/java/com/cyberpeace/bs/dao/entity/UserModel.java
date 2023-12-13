package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 用户模型
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
@TableName("bs_user_model")
public class UserModel extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 简介
     */
    private String description;

    /**
     * 用户类型id
     */
    private String userTypeId;

    /**
     * 访问设备类型
     */
    private Integer accessOs;

    /**
     * 访问配置
     */
    private Integer accessConfig;

    /**
     * 访问行为json
     */
    private String behaviorList;

	/**
	 * 用户数量
	 */
	private Integer userNum;

    /**
     * 访问习惯
     */
    private String accessHabit;

    /**
     * 是否限制带宽
     */
    private Boolean bandwidthLimited;

    /**
     * 访问带宽数值，单位 Mbps
     */
    private Integer bandwidth;

    /**
     * 访问操作系统枚举类
     *
     * @author SN
     * @date 2023/05/06
     */
    public enum AccessOs implements GenericEnum {

        WINDOWS(1, "Windows"), LINUX(2, "Linux"),
        MAC(3, "Mac"), IOS(4, "iOS"), ANDROID(5, "Android");

        /**
         * 枚举值
         */
        @Getter
        private Integer code;

        /**
         * 描述
         */
        @Getter
        private String desc;

        private AccessOs(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 访问配置枚举类
     *
     * @author SN
     * @date 2023/05/06
     */
    public enum AccessConfig implements GenericEnum {

        COSTUMER(1, "自定义"), RANDOM(2, "随机"), NORMAL(3, "正态分布");

        /**
         * 枚举值
         */
        @Getter
        private Integer code;

        /**
         * 描述
         */
        @Getter
        private String desc;

        private AccessConfig(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
