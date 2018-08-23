package com.demo.export.resource;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.common.QueryParameter;
import com.demo.export.dto.LogMessageDTO;
import com.demo.export.service.impl.ExportServiceImpl;

@Component
@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ExportResource {

	@Autowired
	private ExportServiceImpl exportService;
	
	/**
	 * export log
	 * @param parameter
	 * @return
	 */
	@GET
	@Path("/export/log")
	public List<LogMessageDTO> exportLog(@BeanParam QueryParameter parameter){
		//response.setHeader("Content-Disposition", "attachment; filename=mylog.csv");
		//response.setContentType("text/csv");
		return exportService.getLogMessages(parameter.getParameters());
	}
}
