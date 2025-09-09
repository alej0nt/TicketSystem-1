package com.leoalelui.ticketsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: alej0nt
 */
@Data
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
}
