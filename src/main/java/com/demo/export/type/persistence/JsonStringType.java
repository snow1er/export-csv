package com.demo.export.type.persistence;

import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

/**
 * Persistence Entity type
 * It will be mapping to the column type json in pg or mysql 
 * @author Neo.Li
 */
public class JsonStringType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {


	private static final long serialVersionUID = 7024162034156561176L;

	public JsonStringType() {
		super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
	}

	public String getName() {
		return "json";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}

	@Override
	public void setParameterValues(Properties parameters) {
		((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
	}

}
