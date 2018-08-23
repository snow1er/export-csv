package com.demo.export.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Data Transfer Object is used to get log message.
 * @author Neo.Li
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessageDTO implements Serializable{

	private static final long serialVersionUID = -418841001096597876L;

	private UUID logId;
	
	private String message;
	
	private Date genertedTime;
	
	
}
