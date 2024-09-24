package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 镜像分页返回信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "镜像分页返回信息")
public class ImagesByIdVO extends ImageVO {

    // 定制化字段

    /**
     * 业务标签
     */
    @ApiModelProperty(value = "业务标签")
    private Integer businessTag;

    /**
     * 引用过的次数
     */
    @ApiModelProperty(value = "引用过的次数")
    private Integer referenceNums;

    /**
     * 靶标管理中启动过的时长
     */
    @ApiModelProperty(value = "靶标管理 中启动过的时长")
    private Integer useDuration;


    /**
     * UUID
     */
    @ApiModelProperty(value = "UUID")
    private String uuid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 分类
     */
    @ApiModelProperty(value = "一级分类")
    private Integer category;

    /**
     * 子分类
     */
    @ApiModelProperty(value = "二级分类")
    private Integer secondCategory;

    /**
     * 三级分类
     */
    @ApiModelProperty(value = "三级分类")
    private Integer thirdCategory;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String logo;


    /**
     * 角色：网络/网关/终端
     */
    @ApiModelProperty(value = "角色：网络/网关/终端")
    private Integer role;

    /**
     * 镜像类型
     */
    @ApiModelProperty(value = "虚拟类型")
    private String imageType;

    /**
     * 是否导入
     */
    @ApiModelProperty(value = "是否导入")
    private Boolean isImport;

    /**
     * 是否默认靶标
     */
    @ApiModelProperty(value = "是否默认靶标")
    private Boolean isInternal;


    /**
     * 是否初始靶标
     */
    @ApiModelProperty(value = "是否初始靶标")
    private Boolean isInit;

    @ApiModelProperty(value = "是否允许变更iso文件")
    private Boolean allowChangeIso;

    /**
     * 接入方式
     */
    @ApiModelProperty(value = "接入方式")
    private List<AccessProtocol> accessProtocolList;


    /**
     * 系统信息
     */
    @ApiModelProperty(value = "系统信息")
    private ExtSystemInfo extSystemInfo;

    /**
     * 镜像信息
     */
    @ApiModelProperty(value = "镜像信息")
    private ExtImageInfo extImageInfo;

    /**
     * 虚拟机信息
     */
    @ApiModelProperty(value = "虚拟信息")
    private ExtVm extVm;

    /**
     * 网关信息
     */
    @ApiModelProperty(value = "网关信息")
    private ExtGateway extGateway;

    /**
     * 容器信息
     */
    @ApiModelProperty(value = "容器信息")
    private ExtDocker extDocker;


}
