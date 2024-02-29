package com.project.service.business;

import com.project.entity.business.Image;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.business.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));
    }
}
