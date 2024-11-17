package com.cyberpeace.situation.model;

import lombok.Data;

import java.util.List;

@Data
public class VulnTop {
    private String nodeId;

    private Double cvss;

    private List<Vulns> vulnList;

    private List<Defenses> defenseList;

    @Data
    public static class Vulns {
        private String vulnId;

        private String vulnNmae;

        private Double vulnCvss;

        private Integer vulnLevl;
    }

    @Data
    public static class Defenses {
        private String defenseId;

        private String defenseVulnName;

        private String description;
    }
}
