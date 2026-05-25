package ru.mephi.vikingdemo.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "Операции с викингами")
public class VikingController {

    private final VikingService vikingService;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех викингов")
    public List<Viking> getAllVikings() {
        return vikingService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать нового викинга")
    public Viking create(@RequestBody Viking viking) {
        return vikingService.addViking(viking);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить викинга по ID")
    public void delete(@PathVariable int id) {
        vikingService.deleteViking(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить викинга по ID")
    public Viking update(@PathVariable int id,
                         @RequestBody Viking viking) {
        return vikingService.updateViking(id, viking);
    }
}