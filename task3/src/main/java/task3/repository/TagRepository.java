package task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task3.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

}
