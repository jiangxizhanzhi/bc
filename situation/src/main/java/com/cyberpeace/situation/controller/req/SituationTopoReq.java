package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午2:15
 */
@Data
public class SituationTopoReq implements Serializable {

    @NotBlank(groups = {Update.class})
    private String resourceId;

    private String config;

    @NotBlank(groups = {Create.class, Update.class})
    private String templateId;

    private String thumbnail;
}
