package com.bcf.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO implements Serializable {
    private static final long serialVersionUID = -3970204141695530591L;

    private int errorStatusCode;

    private String reason;

}
