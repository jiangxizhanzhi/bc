package com.cyberpeace.integratation.service.bo.situation;

import lombok.Data;

import java.util.List;

@Data
public class RangeData {

    List<ElementData> curLevelData;

    List<ChildLevelData> childLevelData;
}
