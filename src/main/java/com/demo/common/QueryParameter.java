package com.demo.common;

import java.io.Serializable;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * This class wraps query string of HTTP url
 * @author Neo.Li
 */
public class QueryParameter implements Serializable {
	
	private static final long serialVersionUID = -4045772600825019526L;

	/**
	 * Index of page
	 */
	@QueryParam("page")
	private String page;
	
	/**
	 * The size of records returned per page
	 */
	@QueryParam("page_size")
	private String pageSize;
	
	/**
	 * Comma-separated of sorted fields. Ascending order as default, 
	 * to sort in descending order, the field should be start with hyphen
	 */
	@QueryParam("sort")
	private String sort;
	
//	/**
//	 * Comma-separated of filter rules. All supported rules as below:
//	 * <pre>
//	 * 1. Equals - field:value
//	 * 2. Not Equals - field:!value
//	 * 3. Greater Then - field&gt;value
//	 * 4. Greater Then Or Equals - field&gt;:value
//	 * 5. Less Then - field&lt;value
//	 * 6. Less Then Or Equals - field&lt;:value
//	 * 7. Like - field:value (the value should contain * or ?)
//	 * </pre>
//	 */
//	@QueryParam(QueryWrapper.PARAM_FILTER)
//	private String filter;
	
	/**
	 * @exclude
	 */
	@Context
	private UriInfo uriInfo;
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public UriInfo getUriInfo() {
		return uriInfo;
	}
//	public String getFilter() {
//		return filter;
//	}
//	public void setFilter(String filter) {
//		this.filter = filter;
//	}
	
	/**
	 * @exclude
	 */
	public MultivaluedMap<String, String> getParameters() {
		MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
		if (!StringUtils.isEmpty(page)) {
			map.add("page", this.page);
		}
		if (!StringUtils.isEmpty(pageSize)) {
			map.add("page_size", this.pageSize);
		}
		if (!StringUtils.isEmpty(sort)) {
			map.add("sort", this.sort);
		}
//		if (!StringUtils.isEmpty(filter)) {
//			map.add(QueryWrapper.PARAM_FILTER, this.filter);
//		}
		if (uriInfo !=null && uriInfo.getQueryParameters() != null) {
			map.putAll(uriInfo.getQueryParameters());
		}
		return map;
	}
}
