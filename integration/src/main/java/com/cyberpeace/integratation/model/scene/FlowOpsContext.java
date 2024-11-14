package com.cyberpeace.integratation.model.scene;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 流量操作上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class FlowOpsContext extends NodeOpsContext {

    /**
     * 流量id
     */
    private String flow_id;

    /**
     * 源ip地址
     */
    private String source_ip;

    public void setFlowId(String flowId) {
        this.flow_id = flowId;
    }

    public String getFlowId() {
        return this.flow_id;
    }

    public String getSourceIp(){
        return this.source_ip;
    }

    public void setSourceIp(String sourceIp) {
        if (StringUtils.isEmpty(sourceIp)) {
            this.source_ip = null;
        } else {
            this.source_ip = sourceIp;
        }
    }



}
