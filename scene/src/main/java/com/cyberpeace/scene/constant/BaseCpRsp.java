package com.cyberpeace.scene.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :   辛发圣
 * @date :   2023/12/25 15:54
 * @desc :
 **/
@Data
public class BaseCpRsp<T> {

    private T data;

    private Boolean success;

    @JsonProperty("request_id")
    private String requestId;

    private String message;
}
