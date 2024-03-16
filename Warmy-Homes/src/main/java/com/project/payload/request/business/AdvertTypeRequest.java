package com.project.payload.request.business;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Data olur mu araştır
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertTypeRequest {



    @NotBlank(message = "Title cannot be blank")
    @Size(max = 30, message = "Title cannot exceed 30 characters")
    private String title;



}