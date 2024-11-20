package com.cyberpeace.integratation.service.bo;

public class TopoTemplatePageReq {

    private String type;

    private String per_page;

    private String page;

    public String toQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("?per_page=").append(this.per_page);
        builder.append("&page=").append(this.page);
        builder.append("&type=").append(this.type);
        return builder.toString();
    }
}
