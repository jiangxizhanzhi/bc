package com.cyberpeace.integratation.model.scene;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageEvent {

    private String mpSceneId;

    private Integer page;

    private Integer prePage;
}
