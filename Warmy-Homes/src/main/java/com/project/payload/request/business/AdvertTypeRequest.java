package com.project.payload.request.business;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Data olur mu araştır
@Data

public class AdvertTypeRequest {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 30, message = "Title cannot exceed 30 characters")
    private String title;

    @Column(nullable = false)
    private boolean builtIn;

}