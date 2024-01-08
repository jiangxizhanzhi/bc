package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/7 17:35
 * @desc :
 **/
@Data
@TableName("dict_info")
public class DictInfo extends BaseTable {

    @TableField(value = "`key`")
    private String key;

    private String itemName;

    private String itemValue;

    private String parentKey;

    public enum KeyModel{

        AVATAR("avatar", "用户头像"),
        SYSTEM_CONFIG("system_config", "系统配置");

        private String key;
        private String itemName;

        KeyModel(String key, String itemName) {
            this.key = key;
            this.itemName = itemName;
        }
        public String getKey() {
            return key;
        }
        public String getItemName() {
            return itemName;
        }
    }


}
