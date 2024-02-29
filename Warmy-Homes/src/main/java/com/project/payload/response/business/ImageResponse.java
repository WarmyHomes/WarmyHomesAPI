package com.project.payload.response.business;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
    private Long id;
    private byte[] data;
    private String name;
    private String type;
    private boolean featured;
    private int advertId;
    // Diğer özellikler buraya eklenebilir
}
