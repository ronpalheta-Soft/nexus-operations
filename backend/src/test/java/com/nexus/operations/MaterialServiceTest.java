package com.nexus.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository repository;

    @InjectMocks
    private MaterialService service;

    private Material createMaterial(String name, String price, int quantity) {
        Material material = new Material();
        material.setName(name);
        material.setUnitPrice(new BigDecimal(price));
        material.setQuantity(quantity);
        return material;
    }

    @Test
    void shouldCreateMaterialSuccessfully() {
        Material material = createMaterial("  Cement  ", "39.90", 50);

        when(repository.save(any(Material.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Material result = service.create(material);

        assertEquals("Cement", result.getName());
        assertEquals(new BigDecimal("39.90"), result.getUnitPrice());
        assertEquals(50, result.getQuantity());

        verify(repository).save(material);
    }

    @Test
    void shouldFindMaterialById() {
        Material material = createMaterial("Cement", "39.90", 50);

        when(repository.findById(1L)).thenReturn(Optional.of(material));

        Material result = service.findById(1L);

        assertEquals("Cement", result.getName());
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenMaterialDoesNotExist() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.findById(999L)
        );

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void shouldUpdateMaterialSuccessfully() {
        Material existing = createMaterial("Cement", "39.90", 50);
        Material updated = createMaterial("Premium Cement", "45.00", 20);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Material.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Material result = service.update(1L, updated);

        assertEquals("Premium Cement", result.getName());
        assertEquals(new BigDecimal("45.00"), result.getUnitPrice());
        assertEquals(20, result.getQuantity());

        verify(repository).save(existing);
    }

    @Test
    void shouldDeleteExistingMaterial() {
        Material existing = createMaterial("Cement", "39.90", 50);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        service.delete(1L);

        verify(repository).delete(existing);
    }

    @Test
    void shouldFindMaterialsWithLowStock() {
        Material material = createMaterial("Steel Screws", "0.50", 3);

        when(repository.findByQuantityLessThanEqual(5))
                .thenReturn(List.of(material));

        List<Material> result = service.findLowStock();

        assertEquals(1, result.size());
        assertEquals("Steel Screws", result.get(0).getName());

        verify(repository).findByQuantityLessThanEqual(5);
    }
}
