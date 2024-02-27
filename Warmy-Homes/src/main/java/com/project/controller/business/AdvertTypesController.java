package com.project.controller.business;

import com.project.payload.response.business.AdvertTypeResponse;
import com.project.service.business.AdvertTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advert-types")
@RequiredArgsConstructor
public class AdvertTypesController {

    private final AdvertTypesService advertTypesService;


    // @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    @GetMapping
    public ResponseEntity<List<AdvertTypeResponse>> getAllAdvertTypes() {

        List<AdvertTypeResponse> advertTypes = advertTypesService.getAllAdvertTypes();
        return ResponseEntity.ok(advertTypes);

    }



    @GetMapping("/{id}")
    public ResponseEntity<AdvertTypeResponse> getAdvertTypeById(@PathVariable Long id) {
        AdvertTypeResponse advertType = advertTypesService.getAdvertTypeById(id);
        return ResponseEntity.ok(advertType);
    }

}
