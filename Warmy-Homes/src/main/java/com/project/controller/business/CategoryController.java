package com.project.controller.business;

import com.project.payload.request.business.CategoryPropertyKeyRequest;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.Category_Property_Key_Response;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //C01
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategories(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {
        Page<CategoryResponse> categories = categoryService.getCategories(query, page, size, sort, type);
        return ResponseEntity.ok(categories);
    }

    //C02
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {

        Page<CategoryResponse> categories = categoryService.getAllCategories(query, page, size, sort, type);
        return ResponseEntity.ok(categories);
    }

    //C03
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponse);
    }

    //C04
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PostMapping
    public ResponseMessage<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest category) {

        return categoryService.createCategory(category);

    }

    //C05
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseMessage<CategoryResponse> updateCategory(@PathVariable Long id,
                                                            @RequestBody @Valid CategoryRequest request) {
        return  categoryService.updateCategory(id, request);

    }

    //C06
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id) {
        CategoryResponse deletedCategory = categoryService.deleteCategory(id);
        return ResponseEntity.ok(deletedCategory);
    }


    //C07
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/{id}/properties")
    public ResponseEntity<List<Category_Property_Key_Response>>  getCategoryPropertyKeys(@PathVariable Long id) {
        List<Category_Property_Key_Response> propertyKeys = categoryService.findPropertyKeysByCategoryId(id);
        return ResponseEntity.ok(propertyKeys);
    }


    //C08
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PostMapping("/{id}/properties")
    public ResponseMessage<Category_Property_Key_Response>  createPropertyKey(@PathVariable("id") Long categoryId,
                                                                              @Valid @RequestBody CategoryPropertyKeyRequest propertyKeyRequest) {

        return categoryService.createPropertyKey(categoryId, propertyKeyRequest);
    }

    //c09
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PutMapping("/properties/{id}")
    public ResponseMessage<Category_Property_Key_Response>  updatePropertyKey(
            @PathVariable Long id,
            @RequestBody CategoryPropertyKeyRequest request) {

        return categoryService.updatePropertyKey(id, request);

    }

    //C10
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/properties/{id}")
    public ResponseMessage<Category_Property_Key_Response> deletePropertyKey(@PathVariable Long id) {

        return categoryService.deletePropertyKey(id);

    }



    //C11

    @GetMapping("/categories/{slug}")
    public ResponseEntity<CategoryResponse> getCategoryBySlug(@PathVariable String slug) {
        CategoryResponse category = categoryService.getCategoryBySlug(slug);
        return ResponseEntity.ok(category);
    }

}