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
import task3.entity.WayEntity;
import task3.service.WayService;

@RestController
@RequestMapping("api/way")
@RequiredArgsConstructor
public class WayController {

    private final WayService wayService;

    @PostMapping()
    public WayEntity saveWay(@RequestBody WayEntity way) {
        return wayService.saveWay(way);
    }

    @GetMapping()
    public Page<WayEntity> getPaged(@RequestParam("page") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
        Page<WayEntity> result = wayService.getPaged(page, size);
        return result;
    }

    @GetMapping("/{id}")
    public WayEntity getById(@PathVariable(name = "id") Long id) {
        WayEntity result = wayService.getById(id);
        return result;
    }

    @PutMapping("/{id}")
    public WayEntity updateWay(@PathVariable Long id, @RequestBody WayEntity way) {
        return wayService.updateWay(way.setId(id));
    }

    @DeleteMapping("/{id}")
    public void deleteWay(@PathVariable Long id) {
        wayService.deleteWay(new WayEntity().setId(id));
    }
}
