package org.projectAsymmetricKeys.todo.response;

import lombok.*;
import org.projectAsymmetricKeys.category.response.CategoryResponse;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TodoResponse {
    private String ID;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean done;
    private CategoryResponse category;

}
