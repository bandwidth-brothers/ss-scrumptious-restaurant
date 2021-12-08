package com.ss.scrumptious_restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

import com.ss.scrumptious.common_entities.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsTagByType(String s);
    Set<Tag> findAllByTypeIn(List<String> list);
}
