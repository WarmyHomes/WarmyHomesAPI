package com.project.service.business;

import com.project.entity.business.Image;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.response.business.ImageResponse;
import com.project.repository.business.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.entity.enums.ImageType;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    //I-01 /images/:imageId-get
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.IMAGE_NOT_FOUND_MESSAGE, imageId)));
    }




    public List<Long> uploadImages(Long advertId, List<MultipartFile> images) {
        List<Long> uploadedImageIds = new ArrayList<>();

        for (MultipartFile imageFile : images) {
            try {
                byte[] imageData = imageFile.getBytes();
                Image image = new Image();
                image.setData(imageData);
                image.setName(imageFile.getOriginalFilename());
                image.setType(ImageType.IMAGE); // Varsayılan olarak IMAGE tipinde
                image.setFeatured(false); // Öne çıkan özelliği belirleme
                // İlgili reklamı set etmek gerekirse
                // image.setAdvert(advert);
                // Burada ilgili reklama ait olan image'ı kaydedin
                Image savedImage = imageRepository.save(image);
                uploadedImageIds.add(savedImage.getId());
            } catch (IOException e) {
                // Resim yükleme sırasında bir hata oluşursa burada işlenebilir
                // Loglama veya istisna fırlatma gibi işlemler yapılabilir
                String errorMessage = "Resim yükleme sırasında bir hata oluştu: " + e.getMessage();
                System.err.println(errorMessage);
                e.printStackTrace();
            }
        }

        return uploadedImageIds;
    }


    //I-03 /images/:image_ids-delete
    public void deleteImages(List<Long> imageIds) {
        for (Long imageId : imageIds) {
            if (imageRepository.existsById(imageId)) {
                imageRepository.deleteById(imageId);
            } else {
                throw new ResourceNotFoundException(String.format(ErrorMessages.IMAGE_NOT_FOUND_MESSAGE, imageId));

            }
        }
    }


    // I-04 /images/:imageId-put
    public ImageResponse setFeaturedImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));

        // İlgili işlemleri gerçekleştir, örneğin featured alanını ayarla

        // Son olarak, güncellenmiş ImageResponse nesnesini döndür
        return mapToImageResponse(image);
    }

    // Diğer yardımcı metotlar ve mapper fonksiyonları burada olacak
}


}
