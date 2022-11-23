package com.muma.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {

	private String resultCode;
	private String message;
	private Object data;
}
