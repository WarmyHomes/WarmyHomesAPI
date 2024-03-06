package com.project.payload.request.abstracts;

import com.project.entity.business.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@SuperBuilder
public class BaseAdvertRequest extends AbstractAdvertRequest{


}
