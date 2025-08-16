package com.ecommerce.product.service.dtos.usersDtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty("Time")
    private LocalDateTime currentTime;
}
