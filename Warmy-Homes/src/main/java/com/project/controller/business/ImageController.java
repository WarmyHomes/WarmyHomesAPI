package com.project.controller.business;

import com.project.entity.business.Image;
import com.project.payload.response.business.ImageResponse;
import com.project.service.business.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    //I-01 /images/:imageId-get
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    @GetMapping("/{imageId}")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        return ResponseEntity.ok(image);
    }

    //I-02 /images/:advertId-post
    @PreAuthorize("hasAnyAuthority('MANEGER','ADMİN','CUSTOMER')")
    @PostMapping("/{advertId}")
    public ResponseEntity<List<Long>> uploadImages(@PathVariable Long advertId,
                                                   @RequestParam("images") List<MultipartFile> images) {
        List<Long> imageIds = imageService.uploadImages(advertId, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageIds);
    }

    //I-03 /images/:image_ids-delete
    @PreAuthorize("hasAnyAuthority('MANEGER','ADMİN','CUSTOMER')")
    @DeleteMapping("/{imageIds}")
    public ResponseEntity<Void> deleteImages(@PathVariable List<Long> imageIds) {
        imageService.deleteImages(imageIds);
        return ResponseEntity.noContent().build();
    }


        // I-04 /images/:imageId-put
    @PutMapping("/{imageId}")
    public ImageResponse setFeaturedImage(@PathVariable Long imageId) {
        return  imageService.setFeaturedImage(imageId);

    }

}
