package com.cyberpeace.scene.service.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/27 15:51
 * @desc :
 **/
@Data
public class ImageMetadata implements Serializable {

    @JsonProperty("hw_disk_bus")
    private String hwDiskBus;

    @JsonProperty("hw_vif_model")
    private String hwVifModel;

    @JsonProperty("hw_video_model")
    private String hwVideoModel;
}
