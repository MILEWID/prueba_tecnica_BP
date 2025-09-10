package com.pichincha.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private OffsetDateTime timestamp;
    private Integer status;
    private String message;
    private String businessMessage;
    private String code;
    private List<String> details;

    public ErrorDto(OffsetDateTime timestamp, Integer status, String message, String businessMessage) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.businessMessage = businessMessage;
    }
}
