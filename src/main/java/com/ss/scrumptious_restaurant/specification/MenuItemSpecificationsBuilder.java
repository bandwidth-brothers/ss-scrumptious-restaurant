package com.ss.scrumptious_restaurant.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;

public class MenuItemSpecificationsBuilder {
	private final List<SearchCriteria> params;
	private Specification result;
	
    public MenuItemSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public MenuItemSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public MenuItemSpecificationsBuilder search() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
          .map(MenuItemSpecifications::new)
          .collect(Collectors.toList());
        
        result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                  .and(specs.get(i));
        }       
        return this;
    }
    
    public MenuItemSpecificationsBuilder isFromRestaurant(Restaurant rest){
    	Specification<MenuItem> spec = new Specification<MenuItem>() {
    		@Override
    		public Predicate toPredicate(Root<MenuItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    	        
    			return cb.equal(root.get("restaurant").as(Restaurant.class), rest);
    	      }
    	    };
    	
    	result = Specification.where(result)
    			.and(spec);
    	
    	return this;
    }
    
    public Specification<MenuItem> build() {
    	return result;
    }
}
