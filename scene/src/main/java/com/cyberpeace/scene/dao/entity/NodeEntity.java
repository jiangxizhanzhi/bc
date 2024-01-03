package com.cyberpeace.scene.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/22 9:37
 * @desc :
 **/
@Data
@ApiModel
@TableName("cr_node")
@AllArgsConstructor
@NoArgsConstructor
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

    @ApiModelProperty("节点dns")
    private String dns;

    @ApiModelProperty("节点网关")
    private String gateway;

    @ApiModelProperty("节点cpu")
    private Integer cpu;

    @ApiModelProperty("节点内存")
    private Integer memory;

    @ApiModelProperty("节点磁盘")
    private Integer disk;

    @ApiModelProperty("镜像id")
    private String tid;

    @ApiModelProperty("所属场景id")
    private String sid;

    @ApiModelProperty("节点类型,1=网络，5=容器，4=虚拟机")
    private Integer type;

    @ApiModelProperty("节点状态，1=创建中，2=创建完成，3=销毁，4=异常")
    private Integer status;

    @ApiModelProperty("节点地址")
    private String addresses;

    @ApiModelProperty("创建人")
    private String userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
