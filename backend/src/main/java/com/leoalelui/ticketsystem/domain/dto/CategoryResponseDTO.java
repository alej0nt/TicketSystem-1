package com.leoalelui.ticketsystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: alej0nt
 */
@Data
@AllArgsConstructor
public class CategoryResponseDTO {
    private String name;
    private String description;
}
