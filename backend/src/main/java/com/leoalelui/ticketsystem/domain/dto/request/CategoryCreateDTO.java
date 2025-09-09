package com.leoalelui.ticketsystem.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: alej0nt
 */
@Data
@NoArgsConstructor
public class CategoryCreateDTO {

    @NotBlank(message = "The category name cannot be empty")
    private String name;
    @NotBlank(message = "The category description cannot be empty")
    private String description;

}
