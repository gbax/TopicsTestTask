package com.gbax.TopicsTestTask.system.spring;

import org.hibernate.Session;
import org.hibernate.TransactionException;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionDefinition;

/**
 * Created by abayanov
 * Date: 25.08.14
 */
public class HibernateExtendedJpaDialect extends HibernateJpaDialect {
    private Logger logger = LoggerFactory.getLogger(HibernateExtendedJpaDialect.class);

    @Override
    public Object beginTransaction(final EntityManager entityManager,
                                   final TransactionDefinition definition)
            throws PersistenceException, SQLException, TransactionException {
        Session session = (Session) entityManager.getDelegate();
        if (definition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
            getSession(entityManager).getTransaction().setTimeout(definition.getTimeout());
        }

        entityManager.getTransaction().begin();
        logger.debug("Transaction started");

        session.doWork(new Work() {

            public void execute(Connection connection) throws SQLException {
                logger.debug("The connection instance is {}", connection);
                logger.debug("The isolation level of the connection is {} and the isolation level set on the transaction is {}",
                        connection.getTransactionIsolation(), definition.getIsolationLevel());
                DataSourceUtils.prepareConnectionForTransaction(connection, definition);
            }
        });

        return prepareTransaction(entityManager, definition.isReadOnly(), definition.getName());
    }
}
