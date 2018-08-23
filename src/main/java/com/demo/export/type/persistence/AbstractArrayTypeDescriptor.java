package com.demo.export.type.persistence;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

/**
 * Descriptor for sql type array
 * @author Neo.Li
 * @param <T>
 */
public abstract class AbstractArrayTypeDescriptor<T> extends AbstractTypeDescriptor<T>
		implements DynamicParameterizedType {
	
	private static final long serialVersionUID = 3634061524992166199L;
	
	private Class<T> arrayObjectClass;

	@SuppressWarnings("unchecked")
	@Override
	public void setParameterValues(Properties parameters) {
		arrayObjectClass = ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();

	}

	@SuppressWarnings({ "unchecked", "serial" })
	public AbstractArrayTypeDescriptor(Class<T> arrayObjectClass) {
		super(arrayObjectClass, (MutabilityPlan<T>) new MutableMutabilityPlan<Object>() {
			@Override
			protected T deepCopyNotNull(Object value) {
				return arrayDeepCopy(value);
			}
		});

		this.arrayObjectClass = arrayObjectClass;
	}

	@Override
	public boolean areEqual(Object one, Object another) {
		if (one == another) {
			return true;
		}
		if (one == null || another == null) {
			return false;
		}
		if(one.getClass() != another.getClass()) {
			return false;
		}
		return  Arrays.equals( (Object[]) one, (Object[]) another );
	}

	
	@Override
	public String toString(Object value) {
		return Arrays.deepToString((Object[]) value);
	}

	@Override
	public T fromString(String string) {
		return fromString(string, arrayObjectClass);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
		return (X) wrapArray(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> T wrap(X value, WrapperOptions options) {
		if (value instanceof Array) {
			Array array = (Array) value;
			try {
				return unwrapArray((Object[]) array.getArray(), arrayObjectClass);
			} catch (SQLException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return (T) value;
	}

	protected abstract String getSqlArrayType();
	
	
	@SuppressWarnings("unchecked")
	private static <T> T fromString(String string, Class<T> arrayClass) {
		String stringArray = string.replaceAll( "[\\[\\]]", "" );
		String[] tokens = stringArray.split( "," );
		return (T) tokens;
	}

	private Object[] wrapArray(Object objectArray) {
		return (Object[]) objectArray;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	private <T> T unwrapArray(Object[] objectArray, Class<T> arrayClass) {
		return (T) objectArray;
	}
	
	@SuppressWarnings({ "unchecked" })
	private static <T> T arrayDeepCopy(Object objectArray) {
		Object[] array = (Object[]) objectArray;
		return (T) Arrays.copyOf(array, array.length);
	}


}
