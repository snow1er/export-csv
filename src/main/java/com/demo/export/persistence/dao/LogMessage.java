package com.demo.export.persistence.dao;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.demo.export.type.SeverityType;
import com.demo.export.type.persistence.PgSQLEnumType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * An entity mapping log_message table
 * @author Neo.Li
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@TypeDefs({
	@TypeDef(name = "enum", typeClass = PgSQLEnumType.class)
})
@Entity
@Table(name = "log_message")
public class LogMessage implements Serializable{

	private static final long serialVersionUID = 8942629319700642560L;

	@Id
    @Column(name = "log_id", nullable = false)
	private UUID logId;

	@Enumerated(EnumType.STRING)
	@Type(type = "enum")
	@Column(name = "severity_type", nullable = false)
	private SeverityType severityType;
	
	private String message;
	
	@Column(name = "user_id", nullable = false)
	private UUID userId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="generate_time", nullable = false)
	private Date generateTime;
	
}
