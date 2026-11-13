package com.actividad3.crud_spring.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String timestamp, int status, String error,
                            String mensaje, Map<String, String> errores) {
}