// CategoryController.java
package com.project.controller.business;

import com.project.payload.response.business.CategoryResponse;
import com.project.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        List<CategoryResponse> categories = categoryService.getCategories(query, page, size, sort, type);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        List<CategoryResponse> categories = categoryService.getAllCategories(query, page, size, sort, type);
        return ResponseEntity.ok(categories);
    }


}
