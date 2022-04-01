package ru.gb.gbexternalapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.gb.gbexternalapi.dto.enums.Status;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal cost;
    @PastOrPresent
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate manufactureDate;
    @NotNull
    private Status status;
    private String manufacturer;
}
