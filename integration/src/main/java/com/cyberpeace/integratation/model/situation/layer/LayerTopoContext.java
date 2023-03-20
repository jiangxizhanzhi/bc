package com.cyberpeace.integratation.model.situation.layer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class LayerTopoContext implements Serializable {

	private List<AreaContext> areas;

	private List<ElementContext> elements;

	private List<ConnectionContext> connections;

	public TopoContext convert() {
		TopoContext context = new TopoContext();
		context.setConnections(this.connections);
		context.setElements(Maps.newHashMap());
		context.setAreas(Maps.newHashMap());

		this.areas.forEach(area -> {
			context.getAreas().put(area.getAreaId(), area);
		});

		Map<String, List<ElementContext>> layers = Maps.newHashMap();
		this.elements.forEach(element -> {
			if (layers.get(element.getParent()) == null) {
				layers.put(element.getParent(), Lists.newArrayList(element));
			} else {
				layers.get(element.getParent()).add(element);
			}
		});

		layers.forEach((key, elements) -> {
			setPosition(elements, context);
		});

		return context;
	}

	private void setPosition(List<ElementContext> elements, TopoContext context) {

		// 处理节点坐标
		int index = 1;
		int count = 1;
		int interval = 150;
		for (int i = 0; i < elements.size(); i++) {
			ElementContext element = elements.get(i);
			context.getElements().put(element.getElementId(), element);
			if (index == 1) {
				element.setPosition(new PositionContext((float) 0, (float) interval * count));
				index += 1;
			} else if (index == 2) {
				element.setPosition(new PositionContext((float) interval * count, (float) 0));
				index += 1;
			} else if (index == 3) {
				element.setPosition(new PositionContext((float) 0, (float) -interval * count));
				index += 1;
			} else if (index == 4) {
				element.setPosition(new PositionContext((float) -interval * count, (float) 0));
				index = 1;
				count += 1;
			}
		}
	}
}
