package com.nexus.operations;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    public List<Material> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Material findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Material create(@Valid @RequestBody Material material) {
        return service.create(material);
    }

    @PutMapping("/{id}")
    public Material update(
            @PathVariable Long id,
            @Valid @RequestBody Material material) {
        return service.update(id, material);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/low-stock")
    public List<Material> findLowStock() {
        return service.findLowStock();
    }
}
