package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

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

	private Map<String, AreaContext> areas = Maps.newHashMap();

	private List<ElementContext> networks;

	private List<ElementContext> nodes;

	private List<ConnectionContext> connections;

	public TopoContext convert(Boolean adjust, Map<String, ModelTypeContext> modelTypeContextMap,
			Map<String, ModelContext> modelContextMap) {
		TopoContext context = new TopoContext();
		context.setBaseInfo(this.baseInfo);
		context.setConnections(this.connections);
		context.setElements(Maps.newHashMap());
		context.setAreas(Maps.newHashMap());

		// 区域、节点、网络均为空时，直接返回数据
		if (this.areas.isEmpty() && this.nodes.isEmpty() && this.networks.isEmpty()) {
			return context;
		}

		Map<String, ElementContext> elementContextMap = Maps.newHashMap();
		this.networks.forEach(network -> {
			network.setElementType("network");
			network.setModelId(network.getModel());
			network.setDeviceType(getModelType(modelTypeContextMap, network.getDeviceType()));
			network.setModelType(getModelThreeDimensionPic(modelContextMap, network.getModel()));
			context.getElements().put(network.getElementId(), network);
			elementContextMap.put(network.getElementId(), network);
		});
		this.nodes.forEach(node -> {
			node.setElementType("node");
			node.setModelId(node.getModel());
			node.setDeviceType(getModelType(modelTypeContextMap, node.getDeviceType()));
			node.setModelType(getModelThreeDimensionPic(modelContextMap, node.getModel()));
			context.getElements().put(node.getElementId(), node);
			elementContextMap.put(node.getElementId(), node);
		});

		context.setAreas(this.areas);

		if (adjust) {
			this.positionAdjust(elementContextMap);
		}
		return context;
	}

	private String getModelType(Map<String, ModelTypeContext> modelTypeContextMap, String typeId) {
		ModelTypeContext modelTypeContext = modelTypeContextMap.get(typeId);
		return modelTypeContext == null ? typeId : modelTypeContext.getTypeName();
	}

	private String getModelThreeDimensionPic(Map<String, ModelContext> modelContextMap,
			String modelId) {
		ModelContext modelContext = modelContextMap.get(modelId);
		return modelContext == null ? null : modelContext.getThreeDimensionPic();
	}

	private void positionAdjust(Map<String, ElementContext> elementContextMap) {
		Map<String, Element> elementMap = Maps.newHashMap();
		elementContextMap.forEach((elementId, elementContext) -> {
			Element element = new Element(elementContext.getHierarchy().intValue(), elementId,
					elementContext.getPosition().getX(), elementContext.getPosition().getY());
			elementMap.put(elementId, element);
		});
		elementContextMap.forEach((elementId, elementContext) -> {
			Element parent = elementMap.get(elementId); // assert not null
			elementContext.getChild().forEach(childId -> {
				Element child = elementMap.get(childId); // assert not null
				parent.addChild(child);
			});
		});
		elementContextMap.values()
				.forEach(elementContext -> elementContext.getChild().forEach(elementMap::remove));

		List<Element> elements = Lists.newArrayList();
		elements.addAll(elementMap.values());

		Board board = new Board(elements);

		board.getBlocks().forEach(block -> block.getElementPositions().forEach(elementPosition -> {
			String elementId = elementPosition.getElement().getElementId();
			Position position = elementPosition.getPosition();
			ElementContext elementContext = elementContextMap.get(elementId); // assert not null
			PositionContext positionContext = elementContext.getPosition();
			positionContext.setX(position.getCol());
			positionContext.setY(position.getRow());
		}));

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
	@NonNull
	private Float PositionX;

	@Getter
	@NonNull
	private Float PositionY;

	@Getter
	private final List<Element> children = Lists.newArrayList();

	public void addChild(Element child) {
		this.children.add(child);
	}

	public Position getRelativePosition(Position position0) {
		return new Position(getPositionY() - position0.getRow(),
				getPositionX() - position0.getCol());
	}
}

@RequiredArgsConstructor
class Position {

	@Getter
	@NonNull
	private Float row;

	@Getter
	@NonNull
	private Float col;

	public Position add(Position other) {
		return new Position(this.row + other.row, this.col + other.col);
	}

	public Double square() {
		return Math.pow(getRow(), 2) + Math.pow(getCol(), 2);
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
	private Float left;

	@Getter
	@NonNull
	private Float right;

	@Getter
	@NonNull
	private Float top;

	@Getter
	@NonNull
	private Float bottom;

}

/**
 * 表示作为一个整体的由若干个同层级节点组成的节点群组，其内部节点的相对位置是固定的，只能整体平移
 * */
class Block {

	/**
	 * 内部节点及其相对位置的列表
	 * */
	@Getter
	private final List<ElementPosition> elementPositions = Lists.newArrayList();


	/**
	 * Block的绝对位置坐标
	 * */
	@Getter
	@NonNull
	private Position position;

	/**
	 * Block所在的垂直层级
	 * */
	@Getter
	@NonNull
	private final Integer level;


	/**
	 * Block整体的上下左右绝对坐标；
	 * border由position和height width计算得到，它是一个懒加载的缓存属性
	 * 当Block整体移动时，border被置null，position发生改变，等到下次需要用到border信息时再行计算
	 * */
	private Border border;


	/**
	 * 高度
	 * */
	@NonNull
	private final Float height;

	/**
	 * 宽度
	 * */
	@NonNull
	private final Float width;

	public Block(@NonNull Position position, int level, List<Element> elements) {
		if (elements.isEmpty())
			throw new RuntimeException();

		List<Float> xs = new ArrayList<>();
		List<Float> ys = new ArrayList<>();
		elements.forEach(element -> {
			xs.add(element.getPositionX());
			ys.add(element.getPositionY());
		});

		float minY = Collections.min(ys);
		float maxY = Collections.max(ys);
		float minX = Collections.min(xs);
		float maxX = Collections.max(xs);

		this.height = maxY - minY;
		this.width = maxX - minX;

		this.position = position;
		this.level = level;
		elements.forEach(element -> {
			Position relativePosition = element
					.getRelativePosition(new Position(0.5f * (minY + maxY), 0.5f * (minX + maxX)));
			elementPositions.add(new ElementPosition(element, relativePosition, this));
		});
	}

	/**
	 * 给定的Block如果同当前Block无重叠 则返回null
	 * 如果由重叠，则返回一个平移向量建议，给定Block依此平移后可避免同当前Block重叠
	 * */
	@Nullable
	public Position conflict(Block block) {
		if (this == block) {
			return null;
		}

		if (!this.level.equals(block.level)) {
			return null;
		}

		float toLeft = getBorder().getLeft() - block.getBorder().getRight();
		if (toLeft >= 0)
			return null;
		Position ret = new Position(0.0f, toLeft);

		float toRight = getBorder().getRight() - block.getBorder().getLeft();
		if (toRight <= 0)
			return null;
		Position ret2 = new Position(0.0f, toRight);
		if (ret.square() > ret2.square())
			ret = ret2;

		float toTop = getBorder().getTop() - block.getBorder().getBottom();
		if (toTop >= 0)
			return null;
		Position ret3 = new Position(toTop, 0.0f);
		if (ret.square() > ret3.square())
			ret = ret3;

		float toBottom = getBorder().getBottom() - block.getBorder().getTop();
		if (toBottom <= 0)
			return null;
		Position ret4 = new Position(toBottom, 0.0f);
		if (ret.square() > ret4.square())
			ret = ret4;

		return ret;
	}

	/**
	 * Block整体平移
	 * */
	public void move(Position delta) {
		this.position = this.position.add(delta);
		this.border = null;
	}

	/**
	 * 获取当前（平移后）Block的四至坐标
	 * */
	public Border getBorder() {
		if (this.border == null) {
			float minStep = 100;
			this.border = new Border(this.position.getCol() - minStep,
					this.position.getCol() + this.width + minStep, this.position.getRow() - minStep,
					this.position.getRow() + this.height + minStep);
		}
		return this.border;
	}

}

/**
 * 用于盛放Block的容器 可以认为是一块画布
 * */
class Board {

	@Getter
	private final List<Block> blocks = Lists.newArrayList();

	/**
	 * @param initialElements 最顶层的元素 它们可以有子元素 但是不能有父元素 最顶层的元素的level应当一致
	 * */
	public Board(List<Element> initialElements) {
		Integer level = null;
		for (Element element : initialElements) {
			if (level == null) {
				level = element.getLevel();
			} else {
				if (!level.equals(element.getLevel())) {
					throw new RuntimeException(); // 保证initialElements的level一致
				}
			}
		}
		if (level == null)
			throw new RuntimeException();

		List<Block> blockList = new ArrayList<>();
		blockList.add(new Block(new Position(0.0f, 0.0f), level, initialElements));

		while (!blockList.isEmpty()) {
			// blockList中的所有的Block依次加入画布
			for (Block block : blockList) {
				blocks.add(block);
				List<Block> blockList2 = new ArrayList<>();
				blockList2.add(block);
				handlerConflict(blockList2);
			}

			// 获取blockList中所有的Block中的所有的元素，如果该元素有子节点，则把这些子节点作为一个Block添加到新的blockList中去
			List<Block> blockList1 = new ArrayList<>();
			for (Block block : blockList) {
				for (ElementPosition elementPosition : block.getElementPositions()) {
					Element element = elementPosition.getElement();
					if (element.getChildren().isEmpty())
						continue;
					blockList1.add(new Block(elementPosition.getPosition(), element.getLevel(),
							element.getChildren()));
				}
			}
			blockList = blockList1;
		}
	}

	/**
	 * 给定一个Block列表
	 * 逐个放入画布Board 如果和画布中既有的Block位置冲突 则平移既有的Block
	 * */
	private void handlerConflict(List<Block> blockList) {
		while (!blockList.isEmpty()) {
			Block current = blockList.remove(0);
			for (Block anyBlock : this.blocks) {
				Position direction = current.conflict(anyBlock);
				if (direction == null)
					continue;
				anyBlock.move(direction);
				if (current.conflict(anyBlock) != null)
					throw new RuntimeException();

				if (!blockList.contains(anyBlock)) {
					blockList.add(0, anyBlock);
				}
			}
		}
	}
}
