package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;

@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "Операции с викингами")
public class VikingController {

    private final VikingService vikingService;
    private final VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
    }
    
    @GetMapping
    @Operation(summary = "Получить список созданных викингов", operationId = "getAllVikings")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Список успешно получен")})
    public List<Viking> getAllVikings() {
        System.out.println("GET /api/vikings called");
        return vikingService.findAll();
    }

    @GetMapping("/{index}")
    @Operation(summary = "Получить викинга по индексу", operationId = "getVikingByIndex")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Викинг найден"),
        @ApiResponse(responseCode = "404", description = "Викинг не найден")
    })
    public ResponseEntity<Viking> getVikingByIndex(@PathVariable int index) {
        System.out.println("GET /api/vikings/" + index + " called");
        return vikingService.findByIndex(index)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать нового викинга", operationId = "createViking")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Викинг успешно создан"),
        @ApiResponse(responseCode = "400", description = "Викинг с таким именем уже существует")
    })
    public ResponseEntity<Viking> createViking(@RequestBody Viking viking) {
        System.out.println("POST /api/vikings called with: " + viking.name());
        
        if (vikingService.findByName(viking.name()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Viking created = vikingService.addViking(viking);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{index}")
    @Operation(summary = "Обновить викинга по индексу", operationId = "updateViking")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Викинг успешно обновлен"),
        @ApiResponse(responseCode = "404", description = "Викинг не найден")
    })
    public ResponseEntity<Viking> updateViking(@PathVariable int index, @RequestBody Viking updatedViking) {
        System.out.println("PUT /api/vikings/" + index + " called");
        
        boolean updated = vikingService.updateVikingByIndex(index, updatedViking);
        if (updated) {
            return ResponseEntity.ok(updatedViking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{index}")
    @Operation(summary = "Удалить викинга по индексу", operationId = "deleteViking")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Викинг успешно удален"),
        @ApiResponse(responseCode = "404", description = "Викинг не найден")
    })
    public ResponseEntity<Void> deleteViking(@PathVariable int index) {
        System.out.println("DELETE /api/vikings/" + index + " called");
        
        boolean deleted = vikingService.deleteByIndex(index);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/test")
    @Operation(summary = "Получить список тестовых викингов", operationId = "getTest")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Список успешно получен")})
    public List<String> test() {
        System.out.println("GET /api/vikings/test called");
        return List.of("Ragnar", "Bjorn");
    }
    
    @PostMapping("/post")
    @Operation(summary = "Добавить случайного викинга", operationId = "addViaGui")
    public void addViking() {
        System.out.println("POST /api/vikings/post called");
        vikingListener.testAdd();
    }
}