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

		// 默认节点间隔
		int interval = 150;

		// 节点个数
		int n = elements.size();

		// 正方形区域边长
		int m = (int) Math.ceil(Math.sqrt((double) n));

		// 设置坐标池
		List<PositionContext> positionContexts = Lists.newArrayList();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				int x = (i * interval) + (int) (Math.random() * interval);
				int y = (j * interval) + (int) (Math.random() * interval);
				positionContexts.add(new PositionContext((float) x, (float) y));
			}
		}

		// 计算偏移值
		float offset = m * interval / 2;

		// 节点坐标赋值
		for (int i = 0; i < n; i++) {
			ElementContext element = elements.get(i);
			context.getElements().put(element.getElementId(), element);
			PositionContext positionContext = positionContexts.get(i);
			positionContext.setX(positionContext.getX() - offset);
			positionContext.setY(positionContext.getY() - offset);
			element.setPosition(positionContext);
		}
	}
}
