package com.demo.export.service;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MultivaluedMap;

import com.demo.export.dto.LogMessageDTO;

/**
 * This interface will provide query API for LogMessageRepository.
 * @author Neo.Li
 */
public interface ExportService {

	/**
	 * query log messages
	 * @param parameters
	 * @return
	 */
	List<LogMessageDTO> getLogMessages(MultivaluedMap<String, String> parameters);
	
	/**
	 * query log messages by userId
	 * @param userId
	 * @param parameters
	 * @return
	 */
	List<LogMessageDTO> getLogMessagesByUserId(UUID userId, MultivaluedMap<String, String> parameters);
	
}
