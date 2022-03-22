package task3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task3.entity.WayEntity;
import task3.exception.ObjectNotFoundException;
import task3.repository.WayRepository;

@Service
@RequiredArgsConstructor
public class WayService {

    private final WayRepository wayRepository;

    @Transactional
    public WayEntity saveWay(WayEntity way) {
        return wayRepository.save(way);
    }

    @Transactional
    public void deleteWay(WayEntity way) {
        wayRepository.delete(way);
    }

    @Transactional
    public WayEntity updateWay(WayEntity way) {
        return wayRepository.save(way);
    }

    public WayEntity getById(Long id) {
        return wayRepository.findById(id).orElseThrow(()
                -> new ObjectNotFoundException("Way with id " + id + " was not found!"));
    }

    public Page<WayEntity> getPaged(Integer page, Integer size) {
        return wayRepository.findAll(PageRequest.of(page, size));
    }

}
