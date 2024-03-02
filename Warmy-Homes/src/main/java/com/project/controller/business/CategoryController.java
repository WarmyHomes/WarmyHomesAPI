package com.project.controller.business;

import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //C01
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {
        List<CategoryResponse> categories = categoryService.getCategories(query, page, size, sort, type);
        return ResponseEntity.ok(categories);
    }
    //C02
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {
        List<CategoryResponse> categories = categoryService.getAllCategories(query, page, size, sort, type,httpServletRequest);
        return ResponseEntity.ok(categories);
    }

    //C03
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id,
                                                            HttpServletRequest httpServletRequest) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id,httpServletRequest);
        return ResponseEntity.ok(categoryResponse);
    }

    //C04
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PostMapping
    public ResponseMessage<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest category) {
        ResponseMessage<CategoryResponse> createdCategory = categoryService.createCategory(category);
        return createdCategory;
    }

    //C05
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
                                                           @RequestBody @Valid CategoryRequest request) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updatedCategory);
    }

    //C06

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id) {
        CategoryResponse deletedCategory = categoryService.deleteCategory(id);
        return ResponseEntity.ok(deletedCategory);
    }


}
