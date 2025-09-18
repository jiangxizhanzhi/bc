package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.AuthTable;

import com.cyberpeace.common.util.JsonUtil;
import lombok.Data;
import org.apache.shiro.util.CollectionUtils;

import java.net.Inet4Address;
import java.util.List;
import java.util.Objects;

/**
 * 靶场模板公共信息模型
 * 仅使用 operationAuthType， operationAuthIds
 * 不使用 readAuthType， readAuthIds
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 拓扑id
	 */
	private String topoId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 用于关联后台测试场景
	 */
	private String localSceneId;


    /**
     * xd项目给团队训练又细分了type
     */
    private Integer xdTemplateType;

	private Integer xdTemplateCategory;



    public Boolean getPublishedFlag() {

        if (Objects.nonNull(this.getOperationAuthType()) && this.getOperationAuthType().equals(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode())) {
            List<String> shareTeachers = JsonUtil
                    .toJsonObjectArray(this.getOperationAuthIds(), String.class);
            if (CollectionUtils.isEmpty(shareTeachers)) {
                return false;
            }
        }
        return true;
    }


	public static class XdTemplateCategory {
		public static final Integer EXPERIMENT = 1;
		public static final Integer SUBJECT = 2;
		public static final Integer OTHER = 3;
	}


}
