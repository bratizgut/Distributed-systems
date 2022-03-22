package task3.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import task3.entity.NodeEntity;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

    @Query(value = "SELECT * FROM nodes"
            + " WHERE gc_to_sec(earth_distance(ll_to_earth(?1, ?2), ll_to_earth(nodes.lat, nodes.lon))) < ?3"
            + " ORDER BY gc_to_sec(earth_distance(ll_to_earth(?1, ?2), ll_to_earth(nodes.lat, nodes.lon)))"
            + " ASC", nativeQuery = true)
    Collection<NodeEntity> getAllNodesInRadius(Double latitude, Double longitude, Double radius);
}
