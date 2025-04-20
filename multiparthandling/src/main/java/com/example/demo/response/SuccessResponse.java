package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
	private Boolean error;
	private String message;
	private Object data;

}
