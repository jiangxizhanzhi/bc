package com.cyberpeace.scene.service.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/21 20:01
 * @desc :
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Net implements Serializable {

    @JsonProperty("network_id")
    private String network_id;
}
