package com.cyberpeace.oj.course.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * soc请求auth业务对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocRequestAuthBo {

    /**
     * 消息流水号 唯一且自增长
     */
    private String messageSn;

    /**
     * 系统名称 对端身份标识符
     */
    private String systemId;

    /**
     * 密钥哈希 对方分配给己方的密钥哈希值，采用SHA256算法
     * 密钥线下约定，可定期更换
     */
    private String hashCode;
}
