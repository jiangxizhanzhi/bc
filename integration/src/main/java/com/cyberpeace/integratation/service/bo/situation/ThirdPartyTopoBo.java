package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class ThirdPartyTopoBo implements Serializable {

	private SceneContext baseInfo;

	private Map<String, String> areas = Maps.newHashMap();

	private List<ElementContext> networks;

	private List<ElementContext> nodes;

	private List<ConnectionContext> connections;

	public TopoContext convert(Boolean adjust, Map<String, ModelTypeContext> modelTypeContextMap,
			Map<String, ModelContext> modelContextMap) {
		TopoContext context = new TopoContext();
		context.setBaseInfo(this.baseInfo);
		context.setConnections(this.connections);
		context.setElements(Maps.newHashMap());
		Map<String, ElementContext> elementContextMap = Maps.newHashMap();
		this.networks.forEach(network -> {
			network.setElementType("network");
			network.setDeviceType(getModelType(modelTypeContextMap, network.getDeviceType()));
			network.setModel(getModelThreeDimensionPic(modelContextMap, network.getModel()));
			context.getElements().put(network.getElementId(), network);
			elementContextMap.put(network.getElementId(), network);
		});
		this.nodes.forEach(node -> {
			node.setElementType("node");
			node.setDeviceType(getModelType(modelTypeContextMap, node.getDeviceType()));
			node.setModel(getModelThreeDimensionPic(modelContextMap, node.getModel()));
			context.getElements().put(node.getElementId(), node);
			elementContextMap.put(node.getElementId(), node);
		});
		if (adjust) {
			this.positionAdjust(elementContextMap);
		}
		return context;
	}

	private String getModelType(Map<String, ModelTypeContext> modelTypeContextMap, String typeId) {
		ModelTypeContext modelTypeContext = modelTypeContextMap.get(typeId);
		return modelTypeContext == null ? "未知类型" : modelTypeContext.getTypeName();
	}

	private String getModelThreeDimensionPic(Map<String, ModelContext> modelContextMap,
			String modelId) {
		ModelContext modelContext = modelContextMap.get(modelId);
		return modelContext == null ? null : modelContext.getThreeDimensionPic();
	}

	private void positionAdjust(Map<String, ElementContext> elementContextMap) {
		Map<String, Element> elementMap = Maps.newHashMap();
		elementContextMap.forEach((elementId, elementContext) -> {
			Element element = new Element(elementContext.getHierarchy().intValue(), elementId);
			elementMap.put(elementId, element);
		});
		elementContextMap.forEach((elementId, elementContext) -> {
			Element parent = elementMap.get(elementId); // assert not null
			elementContext.getChild().forEach(childId -> {
				Element child = elementMap.get(childId); // assert not null
				parent.addChild(child);
			});
		});
		elementContextMap.values().forEach(elementContext -> {
			elementContext.getChild().forEach(elementMap::remove);
		});

		List<Element> elements = Lists.newArrayList();
		elements.addAll(elementMap.values());

		Board board = new Board(elements);

		board.getBlocks().forEach(block -> {
			block.getElementPositions().forEach(elementPosition -> {
				String elementId = elementPosition.getElement().getElementId();
				Position position = elementPosition.getPosition();
				ElementContext elementContext = elementContextMap.get(elementId); // assert not null
				PositionContext positionContext = elementContext.getPosition();
				positionContext.setX(position.getCol().floatValue() * 100);
				positionContext.setY(position.getRow().floatValue() * 100);
			});
		});

	}

	public TopoContext setAreas(TopoContext topoContext) {

		topoContext.setAreas(Maps.newHashMap());

		this.networks.forEach(network -> {

			AreaContext areaContext = new AreaContext();
			String areaId = network.getElementId() + "area";
			areaContext.setAreaId(areaId);
			areaContext.setHierarchy(network.getHierarchy() + 1);
			areaContext.setName(network.getName() + "区域");

			if (network.getChild().size() >= 2) {
				List<Float> xList = Lists.newArrayList();
				List<Float> yList = Lists.newArrayList();

				network.getChild().forEach(elementId -> {
					xList.add(topoContext.getElements().get(elementId).getPosition().getX());
					yList.add(topoContext.getElements().get(elementId).getPosition().getY());
				});
				areaContext.setScope(getAreaScope(xList, yList));
				topoContext.getAreas().put(areaId, areaContext);
			}
		});

		return topoContext;

	}

	private List<PositionContext> getAreaScope(List<Float> xList, List<Float> yList) {

		// 获取x、y坐标的极值
		Float xMax = 0.0f;
		Float xMin = 0.0f;
		Float yMax = 0.0f;
		Float yMin = 0.0f;

		if (!xList.isEmpty() && !yList.isEmpty()) {
			xMax = Collections.max(xList);
			xMin = Collections.min(xList);
			yMax = Collections.max(yList);
			yMin = Collections.min(yList);
		}

		// 构造拓扑最大坐标范围
		List<PositionContext> sceneScope = Lists.newArrayList();
		sceneScope.add(new PositionContext(xMin, yMin));
		sceneScope.add(new PositionContext(xMin, yMax));
		sceneScope.add(new PositionContext(xMax, yMax));
		sceneScope.add(new PositionContext(xMax, yMin));

		return sceneScope;

	}
}

@RequiredArgsConstructor
class Element {

	@Getter
	@NonNull
	private Integer level;

	@Getter
	@NonNull
	private String elementId;

	@Getter
	private List<Element> children = Lists.newArrayList();

	public void addChild(Element child) {
		this.children.add(child);
	}
}

@RequiredArgsConstructor
class Position {

	@Getter
	@NonNull
	private Integer row;

	@Getter
	@NonNull
	private Integer col;

	public Position add(Position other) {
		return new Position(this.row + other.row, this.col + other.col);
	}
}

@RequiredArgsConstructor
class ElementPosition {

	@Getter
	@NonNull
	private Element element;

	@NonNull
	private Position relativePosition;

	@NonNull
	private Block block;

	public Position getPosition() {
		return this.relativePosition.add(this.block.getPosition());
	}
}

@RequiredArgsConstructor
class Border {

	@Getter
	@NonNull
	private Integer left;

	@Getter
	@NonNull
	private Integer right;

	@Getter
	@NonNull
	private Integer top;

	@Getter
	@NonNull
	private Integer bottom;

}

@RequiredArgsConstructor
class Block {

	@Getter
	private List<ElementPosition> elementPositions = Lists.newArrayList();

	@NonNull
	private Integer rows;

	@NonNull
	private Integer cols;

	@Getter
	@NonNull
	private Position position;

	@Getter
	@NonNull
	private Integer level;

	private Border border;

	public boolean conflict(Block block) {
		if (this == block) {
			return false;
		}

		if (!this.level.equals(block.level)) {
			return false;
		}

		if (((this.getBorder().getLeft() - block.getBorder().getRight())
				* (block.getBorder().getLeft() - this.getBorder().getRight())) < 0) {
			return false;
		}
		return ((this.getBorder().getTop() - block.getBorder().getBottom())
				* (block.getBorder().getTop() - this.getBorder().getBottom())) >= 0;
	}

	public void move(Position delta) {
		this.position = this.position.add(delta);
		this.border = null;
	}

	public Border getBorder() {
		if (this.border == null) {
			this.border = new Border(this.position.getCol(), this.position.getCol() + this.cols - 1,
					this.position.getRow(), this.position.getRow() + this.rows - 1);
		}
		return this.border;
	}

}

class Board {

	@Getter
	private List<Block> blocks = Lists.newArrayList();

	private static Position reshape(int count) {
		int n = (int) Math.floor(Math.pow(count, 0.5));
		if (n * n >= count) {
			return new Position(n, n);
		}
		if ((n + 1) * n >= count) {
			return new Position(n + 1, n);
		}
		return new Position(n + 1, n + 1);
	}

	private static Position calcDirection(Block current, Block anyBlock) {
		if (anyBlock.getPosition().getRow() > current.getPosition().getRow()) {
			return new Position(1, 0);
		}
		if (anyBlock.getPosition().getRow() < current.getPosition().getRow()) {
			return new Position(-1, 0);
		}
		if (anyBlock.getPosition().getCol() > current.getPosition().getCol()) {
			return new Position(0, 1);
		}
		return new Position(0, -1);
	}

	private void putBlock(Position position, int level, List<Element> elements) {
		if (elements.isEmpty()) {
			return;
		}

		Position rowsAndCols = reshape(elements.size());
		int rows = rowsAndCols.getRow();
		int cols = rowsAndCols.getCol();
		Block block = new Block(rows, cols, position, level);
		this.blocks.add(block);

		List<Block> blockList = Lists.newArrayList();
		blockList.add(block);
		while (!blockList.isEmpty()) {
			boolean flag = false;
			for (Block anyBlock : this.blocks) {
				if (blockList.get(0).conflict(anyBlock)) {
					Position direction = calcDirection(blockList.get(0), anyBlock);

					anyBlock.move(direction);
					for (Block block0 : this.blocks) {
						if (block0.conflict(anyBlock)) {
							anyBlock.move(direction);
							break;
						}
					}

					if (!blockList.contains(anyBlock)) {
						blockList.add(anyBlock);
					}

					flag = true;
					break;
				}
			}
			if (!flag) {
				blockList.remove(0);
			}
		}

		int index = 0;
		for (Element element : elements) {
			int row = index / cols; // assert row < rows
			int col = index % cols;
			block.getElementPositions()
					.add(new ElementPosition(element, new Position(row, col), block));
			this.putBlock(new Position(row, col), block.getLevel() + 1, element.getChildren());
			index += 1;
		}

	}

	public Board(List<Element> initialElements) {
		int index = 0;
		for (Element initialElement : initialElements) {
			Position position = new Position(index, 0);
			List<Element> elements = Lists.newArrayList();
			elements.add(initialElement);
			this.putBlock(position, initialElement.getLevel(), elements);
			index += 1;
		}
	}
}
