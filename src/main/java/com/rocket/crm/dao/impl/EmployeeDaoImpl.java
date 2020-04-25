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
import com.rocket.crm.dao.EmployeeDao;
import com.rocket.crm.entity.Branch;
import com.rocket.crm.entity.CityMaster;
import com.rocket.crm.entity.CountryMaster;
import com.rocket.crm.entity.Employee;
import com.rocket.crm.entity.StateMaster;
import com.rocket.crm.entity.User;
import com.rocket.crm.utility.CriteriaOrderBuilderUtility;
import com.rocket.crm.utility.ValidationUtility;

@Component
public class EmployeeDaoImpl implements EmployeeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Map<String, Object>> getAll(String search, boolean advanceSearch, Map<String, Object> context,
			Pageable pageable) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = builder.createTupleQuery();
		Root<Employee> root = criteria.from(Employee.class);
		criteria.multiselect(root.get(AppConstants.FIRSTNAME), root.get(AppConstants.LASTNAME),
				root.get(AppConstants.AVATAR), root.get(AppConstants.EMAIL), root.get(AppConstants.ADDRESS),
				root.get(AppConstants.ACCOUNT_NUMBER), root.get(AppConstants.IFSC_CODE), root.get(AppConstants.SALARY),
				root.get(AppConstants.GENDER), root.get(AppConstants.DOB), root.get(AppConstants.DOJ),
				root.get(AppConstants.MOBILE), root.get(AppConstants.BRANCH), root.get(AppConstants.USER),
				root.get(AppConstants.COUNTRY), root.get(AppConstants.STATE), root.get(AppConstants.CITY),
				root.get(AppConstants.EMPLOYEE_TYPE), root.get(AppConstants.CREATED_BY),
				root.get(AppConstants.CREATED_DATE));

		List<Predicate> predicates = getPredicateEmployee(builder, root, search, advanceSearch, context);

		criteria.where(predicates.toArray(new Predicate[] {}));
		CriteriaOrderBuilderUtility.addOrderBy(builder, criteria, root, pageable);
		List<Tuple> workEffortTuples = entityManager.createQuery(criteria)
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<Map<String, Object>> workEffortMaps = new ArrayList<>();
		for (Tuple tuple : workEffortTuples) {
			Map<String, Object> map = new HashMap<>();
			map.put(AppConstants.FIRSTNAME, tuple.get(0));
			map.put(AppConstants.LASTNAME, tuple.get(1));
			map.put(AppConstants.AVATAR, tuple.get(2));
			map.put(AppConstants.EMAIL, tuple.get(3));
			map.put(AppConstants.ADDRESS, tuple.get(4));
			map.put(AppConstants.ACCOUNT_NUMBER, tuple.get(5));
			map.put(AppConstants.IFSC_CODE, tuple.get(6));
			map.put(AppConstants.SALARY, tuple.get(7));
			map.put(AppConstants.GENDER, tuple.get(8));
			map.put(AppConstants.DOB, tuple.get(9));
			map.put(AppConstants.DOJ, tuple.get(10));
			map.put(AppConstants.MOBILE, tuple.get(11));
			map.put(AppConstants.BRANCH, ((Branch) tuple.get(12)).getName());
			map.put(AppConstants.USERNAME, ((User) tuple.get(13)).getUsername());
			map.put(AppConstants.COUNTRY, ((CountryMaster) tuple.get(14)).getName());
			map.put(AppConstants.STATE, ((StateMaster) tuple.get(15)).getName());
			map.put(AppConstants.CITY, ((CityMaster) tuple.get(16)).getName());
			map.put(AppConstants.EMPLOYEE_TYPE, tuple.get(17));
			map.put(AppConstants.CREATED_BY, ((User) tuple.get(18)).getUsername());
			map.put(AppConstants.CREATED_DATE, tuple.get(19));
			workEffortMaps.add(map);
		}
		long count = entityManager.createQuery(criteria).getResultList().size();
		return new PageImpl<>(workEffortMaps, pageable, count);
	}

	private List<Predicate> getPredicateEmployee(CriteriaBuilder builder, Root<Employee> root, String search,
			boolean advanceSearch, Map<String, Object> context) {
		List<Predicate> predicates = new ArrayList<>();
		// predicates.add(builder.notEqual(root.get(AppConstants.NAME),
		// AppConstants.ROLE_SUPER_ADMIN));
		if (advanceSearch) {
			String firstName = (String) context.get(AppConstants.FIRSTNAME);
			String lastName = (String) context.get(AppConstants.LASTNAME);
			String mobile = (String) context.get(AppConstants.MOBILE);
			String gender = (String) context.get(AppConstants.GENDER);
			String branch = (String) context.get(AppConstants.BRANCH);
			String country = (String) context.get(AppConstants.COUNTRY);
			String state = (String) context.get(AppConstants.STATE);
			String city = (String) context.get(AppConstants.CITY);
			String createdBy = (String) context.get(AppConstants.CREATED_BY);

			if (!ValidationUtility.isEmpty(firstName))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.FIRSTNAME)),
						"%" + firstName.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(lastName))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.LASTNAME)),
						"%" + lastName.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(mobile))
				predicates.add(
						builder.like(builder.lower(root.get(AppConstants.MOBILE)), "%" + mobile.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(gender))
				predicates.add(
						builder.like(builder.lower(root.get(AppConstants.GENDER)), "%" + gender.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(branch))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.BRANCH_ID).get(AppConstants.NAME)),
						"%" + branch.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(country))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.COUNTRY_ID).get(AppConstants.NAME)),
						"%" + country.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(state))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.STATE_ID).get(AppConstants.NAME)),
						"%" + state.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(city))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.CITY_ID).get(AppConstants.NAME)),
						"%" + city.toLowerCase() + "%"));

			if (!ValidationUtility.isEmpty(createdBy))
				predicates.add(builder.like(builder.lower(root.get(AppConstants.CREATED_BY).get(AppConstants.USERNAME)),
						"%" + createdBy.toLowerCase() + "%"));

		} else if (!ValidationUtility.isEmpty(search)) {
			predicates.add(builder.or(
					builder.like(builder.lower(root.<String>get(AppConstants.FIRSTNAME)),
							"%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.LASTNAME)),
							"%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.MOBILE)),
							"%" + search.toLowerCase() + "%"),
					builder.like(root.<String>get(AppConstants.GENDER), "%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.BRANCH).get(AppConstants.NAME)),
							"%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.COUNTRY).get(AppConstants.NAME)),
							"%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.STATE).get(AppConstants.NAME)),
							"%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.CITY).get(AppConstants.NAME)),
							"%" + search.toLowerCase() + "%"),
					builder.like(builder.lower(root.<String>get(AppConstants.CREATED_BY).get(AppConstants.USERNAME)),
							"%" + search.toLowerCase() + "%")));
		}
		return predicates;
	}

}
