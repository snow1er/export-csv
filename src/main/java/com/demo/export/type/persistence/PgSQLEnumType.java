package com.demo.export.type.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Persistence Entity type
 * It will be mapping to the column custom type in pg
 * @author Neo.Li
 * 
 */
public class PgSQLEnumType extends org.hibernate.type.EnumType {


	private static final long serialVersionUID = -2366608271554877517L;

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
		} else {
			st.setObject(index, value.toString(), Types.OTHER);
		}
	}
}
