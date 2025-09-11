package com.leoalelui.ticketsystem.domain.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryUpdateDTO {
    private String name;
    private String description;
}
