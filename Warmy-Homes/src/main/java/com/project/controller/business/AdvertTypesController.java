package com.project.controller.business;

import com.project.service.business.AdvertTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advert-types")
@RequiredArgsConstructor
public class AdvertTypesController {

    private final AdvertTypesService advertTypesService;

    @Autowired
    public AdvertTypeController(AdvertTypeService advertTypeService) {
        this.advertTypeService = advertTypeService;
    }

    @GetMapping
    public ResponseEntity<List<AdvertType>> getAllAdvertTypes() {
        List<AdvertType> advertTypes = advertTypeService.getAllAdvertTypes();
        return ResponseEntity.ok(advertTypes);
    }

}
