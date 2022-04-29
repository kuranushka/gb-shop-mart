package ru.kuranov.model.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.gb.gbapi.common.enums.Status;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductNewDto {

    private Long id;

    @NotBlank(message = "TITLE MUST BE FILLED")
    private String title;

    @NotEmpty(message = "YOU MUST SET AT LEAST ONE CATEGORY IN THE LIST")
    private List<String> categories;

    @NotEmpty(message = "YOU MUST SET MANUFACTURER")
    private List<String> manufacturers;

    @NotBlank(message = "YOU MUST SET MANUFACTURER DATE")
    private String manufactureDate;

    @Min(value = 0L, message = "COST MUST BE GRATER THEN 0")
    private BigDecimal cost;

    private Status status;
}
