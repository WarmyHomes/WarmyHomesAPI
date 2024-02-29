package com.project.service.business;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.AdvertTypeMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.response.business.AdvertTypeResponse;
import com.project.repository.business.AdvertTypesRepository;
import com.project.repository.business.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertTypesService {

    private final AdvertTypesRepository advertTypesRepository;
    private final AdvertTypeMapper advertTypeMapper;




    // T-01 /advert-types-Get
    public List<AdvertTypeResponse> getAllAdvertTypes() {
        // Tüm advert tiplerini veritabanından al
        List<Advert_Type> advertTypes = advertTypesRepository.findAll();

        // AdvertType nesnelerini AdvertTypeResponse nesnelerine dönüştür

             return     advertTypes
                       .stream()
                       .map(advertTypeMapper::mapAdverTypeToAdvertTypeResponse)
                       .collect(Collectors.toList());


    }

        // T-02 /advert-types/:id
    public AdvertTypeResponse getAdvertTypeById(Long id) {

        Advert_Type advert_type=isAdvertTypeExist(id);
        return advertTypeMapper.mapAdverTypeToAdvertTypeResponse(advert_type);


    }


    // T-03 /advert-types Post
    public AdvertTypeResponse createAdvertType(Advert_Type advertType) {
        Advert_Type savedAdvertType = advertTypesRepository.save(advertType);
        return advertTypeMapper.mapAdverTypeToAdvertTypeResponse(savedAdvertType);
    }


    // T-04 /advert-types/:id put
    public AdvertTypeResponse updateAdvertType(Long id, Advert_Type newAdvertType) {
        // Güncellenecek advert tipinin varlığını kontrol edin
        Advert_Type advert_type=isAdvertTypeExist(id);

        // Yeni advert tipi bilgilerini mevcut advert tipine kopyalayın
        advert_type.setTitle(newAdvertType.getTitle());

        // Güncellenmiş advert tipini kaydedin
        Advert_Type updatedAdvertType = advertTypesRepository.save(advert_type);

        // Güncellenmiş advert tipini AdvertTypeResponse nesnesine dönüştürün
        return advertTypeMapper.mapAdverTypeToAdvertTypeResponse(updatedAdvertType);
    }

    //T-05 /advert-types/:id-Delete
    public AdvertTypeResponse deleteAdvertType(Long id) {
        // Silinecek advert tipini bul
        Advert_Type advert_type=isAdvertTypeExist(id);

        // İlişkili kayıtların kontrolü
        // Eğer ilişkili kayıtlar varsa, uygun bir hata fırlatılabilir veya hata mesajı döndürülebilir
        // Burada ilişkili kayıtların kontrolü için örnek bir kod yok, çünkü bu bağlamda ne tür ilişkiler olduğunu bilemiyorum

       /* public boolean isAdvertTypeUsed(Long advertTypeId) {
            // Verilen Advert_Type ID'sine sahip advert var mı kontrol et
            return advertRepository.existsByAdvertType_Id(advertTypeId);
        }
            */



        // İlişkili kayıtların olmadığı varsayılarak, advert tipini sil
        advertTypesRepository.delete(advert_type);

        // Silinen advert tipini AdvertTypeResponse nesnesine dönüştürerek döndür
        return advertTypeMapper.mapAdverTypeToAdvertTypeResponse(advert_type);
    }




    public Advert_Type isAdvertTypeExist(Long id){
        return advertTypesRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id)));
    }
}
