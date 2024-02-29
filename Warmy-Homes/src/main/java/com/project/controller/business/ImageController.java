package com.project.controller.business;

import com.project.entity.business.Image;
import com.project.service.business.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{imageId}")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        return ResponseEntity.ok(image);
    }

    @PostMapping("/{advertId}")
    public ResponseEntity<List<Long>> uploadImages(@PathVariable Long advertId,
                                                   @RequestParam("images") List<MultipartFile> images) {
        List<Long> imageIds = imageService.uploadImages(advertId, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageIds);
    }




}
