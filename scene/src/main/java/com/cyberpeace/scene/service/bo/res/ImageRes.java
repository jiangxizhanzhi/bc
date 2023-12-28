package com.cyberpeace.scene.service.bo.res;

import com.cyberpeace.scene.service.bo.ImageItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/28 9:33
 * @desc :
 **/
@Data
public class ImageRes implements Serializable {

    /**
     * 数量
     */
    private Integer total;

    /**
     * 列表
     */
    private List<ImageItem> items;
}
