package com.bcf.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationTokenResponseDto implements Serializable {
    private static final long serialVersionUID = -7137660987675147604L;
    private String accessToken;
}
