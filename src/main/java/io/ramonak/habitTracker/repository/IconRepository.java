package io.ramonak.habitTracker.repository;

import io.ramonak.habitTracker.entity.Icon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconRepository extends CrudRepository<Icon, Integer> {
}
