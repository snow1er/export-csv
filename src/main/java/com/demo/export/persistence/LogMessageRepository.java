package com.demo.export.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.export.persistence.dao.LogMessage;

/**
 * Domain repository for {@link LogMessage}.
 * Extending the JpaRepository interface provided by spring data jpa.
 * @author Neo.Li
 */
public interface LogMessageRepository extends JpaRepository<LogMessage, UUID>,JpaSpecificationExecutor<LogMessage> {

}

