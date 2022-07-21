package com.challengeone.demo.repository.specification;

import com.challengeone.demo.model.db.UsersEntity;
import com.challengeone.demo.repository.criteria.UsersCriteria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class UsersSpec {

    /**
     * Filtering users by criteria.
     * @param usersCriteria user criteria
     * @return
     */
    public static Specification<UsersEntity> findUsersByCriteria(
        UsersCriteria usersCriteria) {

        return ((Root<UsersEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //service id where clause
            if (!usersCriteria.getFilter().isEmpty()) {

                if (usersCriteria.getFilter().containsKey("lastname")) {
                    predicates.add(cb.equal(root.get("lastname"), usersCriteria.getFilter().get("lastname")));
                }
                if (usersCriteria.getFilter().containsKey("age")) {
                    predicates.add(cb.equal(root.get("age"), usersCriteria.getFilter().get("age")));
                }
            }
            //transform predicates to varargs
            Predicate[] predArray = new Predicate[predicates.size()];
            for (int i = 0; i < predicates.size(); i++) {
                predArray[i] = predicates.get(i);
            }
            //order by
            if (usersCriteria.getSortKey() != null) {
                List<Order> orderList = new ArrayList<>();
                for (String key : usersCriteria.getSortKey().split(",")) {
                    if (key.equalsIgnoreCase("-age")) {
                        orderList.add(cb.desc(root.get("age")));
                    } else if (key.equalsIgnoreCase("age")) {
                        orderList.add(cb.asc(root.get("age")));
                    } else if (key.startsWith("-") ) {
                        orderList.add(cb.desc(root.get("firstname")));
                        orderList.add(cb.desc(root.get("lastname")));
                    } else {
                        orderList.add(cb.asc(root.get("firstname")));
                        orderList.add(cb.asc(root.get("lastname")));
                    }
                }
                query.orderBy(orderList);
            }
            return cb.and(predArray);
        });
    }
}

