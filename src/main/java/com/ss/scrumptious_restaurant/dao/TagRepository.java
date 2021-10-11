package com.ss.scrumptious_restaurant.dao;

import com.ss.scrumptious_restaurant.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsTagByType(String s);
    Set<Tag> findAllByTypeIn(List<String> list);
}
