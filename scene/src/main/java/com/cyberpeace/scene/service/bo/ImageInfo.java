package com.cyberpeace.scene.service.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/27 15:45
 * @desc :
 **/
@Data
public class ImageInfo implements Serializable {

    private Integer id;

    private String uuid;

    private String user;

    private String project;

    private String name;

    private Integer size;

    @JsonProperty("virtual_size")
    private Integer virtualSize;

    private String status;

    private String osType;

    @JsonProperty("disk_format")
    private String diskFormat;

    private String checksum;

    @JsonProperty("min_disk")
    private Integer minDisk;

    @JsonProperty("min_memory")
    private Integer minMemory;

    @JsonProperty("min_cpu")
    private Integer minCpu;

    @JsonProperty("image_metadata")
    private ImageMetadata imageMetadata;

    private String description;

    private String vm;

    private String location;

    @JsonProperty("hw_qemu_guest_agent")
    private Boolean hwQemuGuestAgent;
}
