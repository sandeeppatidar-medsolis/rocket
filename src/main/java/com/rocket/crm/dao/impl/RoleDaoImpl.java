package com.rocket.crm.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.rocket.crm.constants.AppConstants;
import com.rocket.crm.dao.RoleDao;
import com.rocket.crm.entity.Role;
import com.rocket.crm.utility.CriteriaOrderBuilderUtility;
import com.rocket.crm.utility.ValidationUtility;

@Component
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Map<String, Object>> getAll(String search, boolean advanceSearch, Map<String, Object> context,
			Pageable pageable) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = builder.createTupleQuery();
		Root<Role> root = criteria.from(Role.class);
		criteria.multiselect(root.get(AppConstants.NAME), root.get(AppConstants.DISPLAY_NAME));

		List<Predicate> predicates = getPredicateRole(builder, root, search, advanceSearch, context);

		criteria.where(predicates.toArray(new Predicate[] {}));
		CriteriaOrderBuilderUtility.addOrderBy(builder, criteria, root, pageable);
		List<Tuple> workEffortTuples = entityManager.createQuery(criteria)
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<Map<String, Object>> workEffortMaps = new ArrayList<>();
		for (Tuple tuple : workEffortTuples) {
			Map<String, Object> map = new HashMap<>();
			map.put(AppConstants.NAME, tuple.get(0));
			map.put(AppConstants.DISPLAY_NAME, tuple.get(1));
			workEffortMaps.add(map);
		}
		long count = entityManager.createQuery(criteria).getResultList().size();
		return new PageImpl<>(workEffortMaps, pageable, count);
	}

	private List<Predicate> getPredicateRole(CriteriaBuilder builder, Root<Role> root, String search,
			boolean advanceSearch, Map<String, Object> context) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(root.get(AppConstants.NAME), AppConstants.ROLE_SUPER_ADMIN));
		if (advanceSearch) {
			String name = (String) context.get(AppConstants.NAME);
			String displayName = (String) context.get(AppConstants.DISPLAY_NAME);

			if (!ValidationUtility.isEmpty(name))
				predicates
						.add(builder.like(builder.lower(root.get(AppConstants.NAME)), "%" + name.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(displayName))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.DISPLAY_NAME)),
						"%" + displayName.toLowerCase() + "%"));

		} else if (!ValidationUtility.isEmpty(search)) {
			predicates.add(builder.or(
					builder.like(builder.lower(root.<String>get(AppConstants.NAME)), "%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.DISPLAY_NAME)),
							"%" + search.toLowerCase() + "%")));
		}
		return predicates;
	}

}
