package com.demo.export.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.common.StringUtils;
import com.demo.export.dto.LogMessageDTO;
import com.demo.export.persistence.LogMessageRepository;
import com.demo.export.persistence.dao.LogMessage;
import com.demo.export.service.ExportService;

/**
 * A implement to <code>CsvExportService</code>
 * @author Neo.Li
 */
@Service
public class ExportServiceImpl implements ExportService{

	@Autowired
	private LogMessageRepository logMessageRepository;

	@Override
	public List<LogMessageDTO> getLogMessages(MultivaluedMap<String, String> parameters) {
		int page = 0;
		int pageSize = 20;
		if(parameters.containsKey("page")) {
			page = Integer.valueOf(parameters.getFirst("page"));
		}
		if(parameters.containsKey("page_size")) {
			pageSize = Integer.valueOf(parameters.getFirst("page_size"));
		}
		Pageable pageable = new PageRequest(page, pageSize);
		//Page<LogMessage> findAll = logMessageRepository.findAll(pageable);
		//return findAll.getContent().stream().map(log -> convertToDto(log)).collect(Collectors.toList());
		return Arrays.asList(mockLog(), mockLog());
	}

	
	@Override
	public List<LogMessageDTO> getLogMessagesByUserId(UUID userId, MultivaluedMap<String, String> parameters) {
		int page = 0;
		int pageSize = 20;
		if(parameters.containsKey("page")) {
			page = Integer.valueOf(parameters.getFirst("page"));
		}
		if(parameters.containsKey("page_size")) {
			pageSize = Integer.valueOf(parameters.getFirst("page_size"));
		}
		Pageable pageable = new PageRequest(page, pageSize);
		/*Page<LogMessage> findAll = logMessageRepository.findAll(new Specification<LogMessage>() {

			@Override
			public Predicate toPredicate(Root<LogMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("userId"), userId);
				return cb.and(predicate);
			}
			
		}, pageable);
		return findAll.getContent().stream().map(log -> convertToDto(log)).collect(Collectors.toList());*/
		return Arrays.asList(mockLog(), mockLog());
	}


	private LogMessageDTO convertToDto(LogMessage log) {
		return new LogMessageDTO();
	}
	
	private LogMessageDTO mockLog() {
		LogMessageDTO logDto = new LogMessageDTO();
		logDto.setLogId(UUID.randomUUID());
		logDto.setMessage(StringUtils.getRandomString(30));
		logDto.setGenertedTime(new Date());
		return logDto;
	}
}
