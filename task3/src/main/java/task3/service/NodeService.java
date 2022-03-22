package task3.service;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task3.entity.NodeEntity;
import task3.exception.ObjectNotFoundException;
import task3.repository.NodeRepository;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final NodeRepository nodeRepository;

    @Transactional
    public NodeEntity saveNode(NodeEntity node) {
        return nodeRepository.save(node);
    }

    @Transactional
    public void deleteNode(NodeEntity node) {
        nodeRepository.delete(node);
    }

    @Transactional
    public NodeEntity updateNode(NodeEntity node) {
        return nodeRepository.save(node);
    }

    public Collection<NodeEntity> getNodesInRadius(Double lat, Double lon, Double radius) {
        return nodeRepository.getAllNodesInRadius(lat, lon, radius);
    }

    public NodeEntity getById(Long id) {
        return nodeRepository.findById(id).orElseThrow(()
                -> new ObjectNotFoundException("Node with id " + id + " was not found!"));
    }

    public Page<NodeEntity> getPaged(Integer page, Integer size) {
        return nodeRepository.findAll(PageRequest.of(page, size));
    }

}
