package task3.controller;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task3.entity.NodeEntity;
import task3.service.NodeService;

@RestController
@RequestMapping("/api/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;

    @PostMapping()
    public NodeEntity saveNode(@RequestBody NodeEntity node) {
        return nodeService.saveNode(node);
    }

    @GetMapping()
    public Page<NodeEntity> getPaged(@RequestParam("page") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
        Page<NodeEntity> result = nodeService.getPaged(page, size);
        return result;
    }

    @GetMapping("/{id}")
    public NodeEntity getById(@PathVariable(name = "id") Long id) {
        NodeEntity result = nodeService.getById(id);
        return result;
    }

    @GetMapping("radius")
    public NodeEntity[] getNodesInRadius(@RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon,
            @RequestParam("radius") Double radius) {
        Collection<NodeEntity> nodeCollection = nodeService.getNodesInRadius(lat, lon, radius);
        return nodeCollection.toArray(new NodeEntity[nodeCollection.size()]);
    }

    @PutMapping("/{id}")
    public NodeEntity updateNode(@PathVariable Long id, @RequestBody NodeEntity node) {
        return nodeService.updateNode(node.setId(id));
    }

    @DeleteMapping("/{id}")
    public void deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(new NodeEntity().setId(id));
    }
}
