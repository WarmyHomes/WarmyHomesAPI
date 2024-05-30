package com.project.payload.response.business;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    private Long id;
    private String name;
    private String type; // Enum olarak saklandığı varsayılarak
    private Boolean featured;
}
