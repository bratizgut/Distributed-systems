package task3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task3.entity.RelationEntity;
import task3.exception.ObjectNotFoundException;
import task3.repository.RelationRepository;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final RelationRepository relationRepository;

    @Transactional
    public RelationEntity saveRelation(RelationEntity relation) {
        return relationRepository.save(relation);
    }

    @Transactional
    public void deleteRelation(RelationEntity relation) {
        relationRepository.delete(relation);
    }

    @Transactional
    public RelationEntity updateRelation(RelationEntity relation) {
        return relationRepository.save(relation);
    }

    public RelationEntity getById(Long id) {
        return relationRepository.findById(id).orElseThrow(()
                -> new ObjectNotFoundException("Relation with id " + id + " was not found!"));
    }

    public Page<RelationEntity> getPaged(Integer page, Integer size) {
        return relationRepository.findAll(PageRequest.of(page, size));
    }

}
