package com.cyberpeace.integratation.model.scene;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class FlowOpsContext extends NodeOpsContext {

    private String flow_id;

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
