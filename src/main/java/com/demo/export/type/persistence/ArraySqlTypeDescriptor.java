package com.demo.export.type.persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

/**
 * Descriptor for pg sql type array
 * @author Neo.Li
 */
public class ArraySqlTypeDescriptor implements SqlTypeDescriptor {

	private static final long serialVersionUID = 5477413655425003102L;
	
	public static final ArraySqlTypeDescriptor INSTANCE = new ArraySqlTypeDescriptor();
	@Override
	public int getSqlType() {
		return Types.ARRAY;
	}

	@Override
	public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicBinder<X>(javaTypeDescriptor, this) {
			
			@Override
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)throws SQLException {
				@SuppressWarnings("unchecked")
				AbstractArrayTypeDescriptor<Object> abstractArrayTypeDescriptor = (AbstractArrayTypeDescriptor<Object>) javaTypeDescriptor;
				st.setArray(index, st.getConnection().createArrayOf(abstractArrayTypeDescriptor.getSqlArrayType(),
						abstractArrayTypeDescriptor.unwrap(value, Object[].class, options)));
			}

			@Override
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				throw new UnsupportedOperationException("Binding by name is not supported!");
			}
		};
	}

	@Override
	public boolean canBeRemapped() {
		return true;
	}

	@Override
	public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicExtractor<X>(javaTypeDescriptor, this) {

			@Override
			protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(rs.getArray(name), options);
			}

			@Override
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(statement.getArray(index), options);
			}

			@Override
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options)throws SQLException {
				return javaTypeDescriptor.wrap(statement.getArray(name), options);
			}
		};
	}

}
