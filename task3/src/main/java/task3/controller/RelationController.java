package task3.controller;

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
import task3.entity.RelationEntity;
import task3.service.RelationService;

@RestController
@RequestMapping("/api/relation")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @PostMapping()
    public RelationEntity saveRelation(@RequestBody RelationEntity relation) {
        return relationService.saveRelation(relation);
    }

    @GetMapping()
    public Page<RelationEntity> getPaged(@RequestParam("page") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
        Page<RelationEntity> result = relationService.getPaged(page, size);
        return result;
    }

    @GetMapping("/{id}")
    public RelationEntity getById(@PathVariable(name = "id") Long id) {
        RelationEntity result = relationService.getById(id);
        return result;
    }

    @PutMapping("/{id}")
    public RelationEntity updateRelation(@PathVariable Long id, @RequestBody RelationEntity relation) {
        return relationService.updateRelation(relation.setId(id));
    }

    @DeleteMapping("/{id}")
    public void deleteRelation(@PathVariable Long id) {
        relationService.deleteRelation(new RelationEntity().setId(id));
    }
}
