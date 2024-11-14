package com.cyberpeace.oj.ojbase.service.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 集合上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionContext implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 业务类型
     */
    private Integer businessType;

}
