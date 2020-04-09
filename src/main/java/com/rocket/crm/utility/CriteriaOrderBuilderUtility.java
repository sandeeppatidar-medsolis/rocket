package com.rocket.crm.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class CriteriaOrderBuilderUtility {

	private CriteriaOrderBuilderUtility() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addOrderBy(CriteriaBuilder builder, CriteriaQuery criteria, Root root, Pageable pageable) {
		if (!pageable.getSort().isEmpty()) {
			Iterator<Order> it = pageable.getSort().iterator();
			List<javax.persistence.criteria.Order> ordersList = new ArrayList<>();
			while (it.hasNext()) {
				addOrderByLoop(builder, root, it, ordersList);
			}
			if (!ordersList.isEmpty()) {
				criteria.orderBy(ordersList);
			}
		}
	}

	private static void addOrderByLoop(CriteriaBuilder builder, @SuppressWarnings("rawtypes") Root root,
			Iterator<Order> it, List<javax.persistence.criteria.Order> ordersList) {
		Order order = it.next();
		if (order.getProperty().contains(".")) {
			String[] splitProps = order.getProperty().split("\\.");
			ordersList.add(
					order.getDirection().equals(Direction.ASC) ? builder.asc(root.get(splitProps[0]).get(splitProps[1]))
							: builder.desc(root.get(splitProps[0]).get(splitProps[1])));
		} else {
			ordersList.add(order.getDirection().equals(Direction.ASC) ? builder.asc(root.get(order.getProperty()))
					: builder.desc(root.get(order.getProperty())));
		}
	}

	public static boolean isAnyPresentSortProperties(Pageable pageable, List<String> properties) {
		if (pageable.getSort().isEmpty()) {
			return false;
		}
		Iterator<Order> it = pageable.getSort().iterator();
		boolean isPresent = false;
		while (it.hasNext()) {
			if (properties.contains(it.next().getProperty())) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	public static Direction getSortPropertyDirection(Pageable pageable, String property) {
		if (pageable.getSort().isEmpty()) {
			return Direction.ASC;
		}
		Iterator<Order> it = pageable.getSort().iterator();
		Direction direction = Direction.ASC;
		while (it.hasNext()) {
			Order order = it.next();
			if (property.equals(order.getProperty())) {
				direction = order.getDirection();
				break;
			}
		}
		return direction;
	}

	public static Direction getFirstSortDirection(Pageable pageable) {
		if (pageable.getSort().isEmpty()) {
			return Direction.ASC;
		}
		Iterator<Order> it = pageable.getSort().iterator();
		if (it.hasNext()) {
			return it.next().getDirection();
		}
		return Direction.ASC;
	}

	public static String getFirstSortProperty(Pageable pageable) {
		if (pageable.getSort().isEmpty()) {
			return null;
		}
		Iterator<Order> it = pageable.getSort().iterator();
		if (it.hasNext()) {
			return it.next().getProperty();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static void addOrderWithJoin(CriteriaBuilder builder, CriteriaQuery criteria, Join join, Pageable pageable) {
		if (pageable.getSort().isEmpty()) {
			return;
		}
		Iterator<Order> it = pageable.getSort().iterator();
		if (it.hasNext()) {
			Order order = it.next();
			if (order.getProperty().contains(".")) {
				String[] splitProps = order.getProperty().split("\\.");
				criteria.orderBy(order.getDirection().equals(Direction.ASC)
						? builder.asc(join.get(splitProps[0]).get(splitProps[1]))
						: builder.desc(join.get(splitProps[0]).get(splitProps[1])));
			} else {
				criteria.orderBy(order.getDirection().equals(Direction.ASC) ? builder.asc(join.get(order.getProperty()))
						: builder.desc(join.get(order.getProperty())));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void addOrderWithJoin(CriteriaBuilder builder, CriteriaQuery criteria, Join join, String property,
			Direction direction) {
		if (property == null || direction == null) {
			return;
		}

		if (property.contains(".")) {
			String[] splitProps = property.split("\\.");
			criteria.orderBy(direction.equals(Direction.ASC) ? builder.asc(join.get(splitProps[0]).get(splitProps[1]))
					: builder.desc(join.get(splitProps[0]).get(splitProps[1])));
		} else {
			criteria.orderBy(direction.equals(Direction.ASC) ? builder.asc(join.get(property))
					: builder.desc(join.get(property)));
		}

	}

	@SuppressWarnings("rawtypes")
	public static void addOrderWithRoot(CriteriaBuilder builder, CriteriaQuery criteria, Root join, String property,
			Direction direction) {
		if (property == null || direction == null) {
			return;
		}

		if (property.contains(".")) {
			String[] splitProps = property.split("\\.");
			criteria.orderBy(direction.equals(Direction.ASC) ? builder.asc(join.get(splitProps[0]).get(splitProps[1]))
					: builder.desc(join.get(splitProps[0]).get(splitProps[1])));
		} else {
			criteria.orderBy(direction.equals(Direction.ASC) ? builder.asc(join.get(property))
					: builder.desc(join.get(property)));
		}

	}
}