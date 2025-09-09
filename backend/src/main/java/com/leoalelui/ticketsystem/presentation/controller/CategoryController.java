package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> save (@RequestBody CategoryCreateDTO categoryCreateDTO) {
        return ResponseEntity.ok(categoryService.save(categoryCreateDTO));
    }
}
