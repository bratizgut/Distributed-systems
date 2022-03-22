package task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task3.entity.RelationEntity;

public interface RelationRepository extends JpaRepository<RelationEntity, Long> {

}
