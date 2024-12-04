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
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionContext implements Serializable {

    /**
     * 用户id
     *
     * @see String
     */
    private String userId;

    /**
     * 业务id
     *
     * @see String
     */
    private String businessId;

    /**
     * 业务类型
     *
     * @see Integer
     */
    private Integer businessType;

}
