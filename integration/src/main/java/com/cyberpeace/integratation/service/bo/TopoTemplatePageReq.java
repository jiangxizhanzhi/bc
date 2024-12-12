package com.cyberpeace.integratation.service.bo;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopoTemplatePageReq {

    private String type;

    private String per_page;

    private String page;
    
    private String search;

    public String toQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("?per_page=").append(this.per_page);
        builder.append("&page=").append(this.page);
        builder.append("&type=").append(this.type);
		if (StringUtils.isNotBlank(this.search)) {
			builder.append("&search=").append(this.search);
		}
        return builder.toString();
    }
}
