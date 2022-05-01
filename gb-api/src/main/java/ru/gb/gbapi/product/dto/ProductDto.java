package ru.gb.gbapi.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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
    @Digits(integer=3, fraction=2)
    private BigDecimal cost;

    @PastOrPresent
    @JsonFormat(pattern="dd.MM.yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate manufactureDate;

    @NotNull
    private Status status;

    private ManufacturerDto manufacturer;

    private Set<CategoryDto> categories;
}
