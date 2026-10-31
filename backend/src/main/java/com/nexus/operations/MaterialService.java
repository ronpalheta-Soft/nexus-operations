package com.nexus.operations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class MaterialService {

    private static final int LOW_STOCK_LIMIT = 5;

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Material> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Material findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Material not found"));
    }

    public Material create(Material material) {
        material.setName(material.getName().trim());
        return repository.save(material);
    }

    public Material update(Long id, Material updatedMaterial) {
        Material existing = findById(id);

        existing.setName(updatedMaterial.getName().trim());
        existing.setUnitPrice(updatedMaterial.getUnitPrice());
        existing.setQuantity(updatedMaterial.getQuantity());

        return repository.save(existing);
    }

    public void delete(Long id) {
        Material existing = findById(id);
        repository.delete(existing);
    }

    @Transactional(readOnly = true)
    public List<Material> findLowStock() {
        return repository.findByQuantityLessThanEqual(LOW_STOCK_LIMIT);
    }
}
