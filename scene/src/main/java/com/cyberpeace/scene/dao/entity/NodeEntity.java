package com.cyberpeace.scene.dao.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.service.bo.interactive.Access;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/22 9:37
 * @desc :
 **/
@Data
@ApiModel
@TableName("cr_node")
public class NodeEntity extends BaseTable implements Serializable {

    @ApiModelProperty("节点id")
    @TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("虚拟机或容器id")
    private String instanceId;

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("节点ip")
    private String ip;

    @ApiModelProperty("镜像id")
    private String tid;

    @ApiModelProperty("所属场景id")
    private String sid;

	@ApiModelProperty("节点类型,1=黑客机器，2=受害者机器")
    private Integer type;

    @ApiModelProperty("节点状态，0:运行，1：关闭，2：创建中，3:删除，4：错误")
    private Integer status;

	@ApiModelProperty("image id")
    private String nid;

	/**
	 * 所属情景
	 */
	private Integer scenario;

	/**
	 * 接入方式
	 */
	private String access;

	public List<Access> getAccessModes() {
		return JsonUtil.toJsonObjectArray(this.access, Access.class);
	}
}
