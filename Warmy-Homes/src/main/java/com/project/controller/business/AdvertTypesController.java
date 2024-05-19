package com.project.controller.business;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.payload.request.business.AdvertTypeRequest;
import com.project.payload.response.business.AdvertTypeResponse;
import com.project.service.business.AdvertTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("advert-types")
@RequiredArgsConstructor
public class AdvertTypesController {

    private final AdvertTypesService advertTypesService;


    // T-01 /advert-types-Get Reklam türlerini döndürmelidir
//    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    @GetMapping
    public ResponseEntity<List<AdvertTypeResponse>> getAllAdvertTypes() {
        List<AdvertTypeResponse> advertTypes = advertTypesService.getAllAdvertTypes();
        return ResponseEntity.ok(advertTypes);

    }


    // T-02 /advert-types/:id
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AdvertTypeResponse> getAdvertTypeById(@PathVariable Long id) {
        AdvertTypeResponse advertType = advertTypesService.getAdvertTypeById(id);
        return ResponseEntity.ok(advertType);
    }


    // T-03 /advert-types Post  Bir reklam türü oluşturacak
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PostMapping
    public ResponseEntity<AdvertTypeResponse> createAdvertType(@RequestBody AdvertTypeRequest advertType) {
        AdvertTypeResponse createdAdvertType = advertTypesService.createAdvertType(advertType);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdvertType);
    }


    // T-04 /advert-types/:id put Bir reklam türünü güncelleyecek rr

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AdvertTypeResponse> updateAdvertType(@PathVariable Long id,
                                                               @RequestBody  @Valid AdvertTypeRequest advertType) {
        AdvertTypeResponse updatedAdvertType = advertTypesService.updateAdvertType(id, advertType);
        return ResponseEntity.ok(updatedAdvertType);
    }

    //T-05 /advert-types/:id-Delete
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<AdvertTypeResponse> deleteAdvertType(@PathVariable Long id) {
        AdvertTypeResponse deletedAdvertType = advertTypesService.deleteAdvertType(id);
        return ResponseEntity.ok(deletedAdvertType);
    }




}
