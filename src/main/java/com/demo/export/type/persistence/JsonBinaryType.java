package com.demo.export.type.persistence;

import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

/**
 * Persistence Entity type
 * It will be mapping to the column type jsonb in pg or mysql 
 * @author Neo.Li
 */
public class JsonBinaryType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {


	private static final long serialVersionUID = -6241641394290253641L;

	public JsonBinaryType() {
		super(JsonBinarySqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
	}

	public String getName() {
		return "jsonb";
	}

	@Override
	public void setParameterValues(Properties parameters) {
		((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
	}

}
