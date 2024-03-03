package com.project.payload.response.business;

import com.project.entity.business.Advert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAdvertsReportResponse {

    private List<Advert> adverts;
}
