package com.project.payload.mappers;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.payload.request.business.AdvertTypeRequest;
import com.project.payload.response.business.AdvertTypeResponse;
import org.springframework.stereotype.Component;


@Component
public class AdvertTypeMapper {

    public AdvertTypeResponse mapAdverTypeToAdvertTypeResponse(Advert_Type advertType) {
          return       AdvertTypeResponse.builder()
                        .id(advertType.getId())
                        .title(advertType.getTitle())
                        .build();
    }


    public Advert_Type mapadvertTypeRequestToAdvertType(AdvertTypeRequest advertTypeRequest) {
        return Advert_Type.builder()

                .title(advertTypeRequest.getTitle())
                .builtIn(false)
                .build();
    }

}
