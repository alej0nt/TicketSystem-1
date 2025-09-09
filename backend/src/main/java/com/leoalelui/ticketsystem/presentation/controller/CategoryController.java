package com.leoalelui.ticketsystem.presentation.controller;

import com.leoalelui.ticketsystem.domain.dto.request.CategoryCreateDTO;
import com.leoalelui.ticketsystem.domain.dto.response.CategoryResponseDTO;
import com.leoalelui.ticketsystem.domain.service.CategoryService;
import lombok.AllArgsConstructor;
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
