package com.cyberpeace.rtks.constant;

/**
 *
 * 靶场自定义错误码。
 *  1、从100001开始递增编号；
 *  2、截止编号地址100999；
 *
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 *
 */
public interface RtksBusinessErrorCode {

    /** RTKS框架管理中，当前层级下存在角色的时候，用户试图删除该层级*/
    String UNABLE_DELETE_FRAME = "100001";

    /** 已经绑定了岗位的层级不允许新增子层级*/
    String UNABLE_ADD_FRAME = "100002";
}
