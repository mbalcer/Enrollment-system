package pl.mbalcer.enrollmentsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime timeOfPublication;
}
