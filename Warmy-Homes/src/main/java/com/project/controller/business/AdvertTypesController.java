package com.project.controller.business;

import com.project.payload.response.business.AdvertTypeResponse;
import com.project.service.business.AdvertTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advert-types")
@RequiredArgsConstructor
public class AdvertTypesController {

    private final AdvertTypesService advertTypesService;




    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    @GetMapping
    public Page<AdvertTypeResponse> getAllAdvertTypes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        return null;

    }

}
