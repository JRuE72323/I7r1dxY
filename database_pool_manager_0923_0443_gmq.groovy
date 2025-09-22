// 代码生成时间: 2025-09-23 04:43:16
import groovy.sql.Sql
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException

/**
 * Database Pool Manager Configuration
 * This class configures and manages the database connection pool using Spring and Grails
 */
@Configuration
class DatabasePoolManager {

    /**
     * Configures and returns a DataSource for the database connection pool
     *
     * @return DataSource - Database connection pool
     */
    @Bean
    DataSource dataSource() {
        def dataSource = new DriverManagerDataSource()
        dataSource.setDriverClassName('com.mysql.cj.jdbc.Driver') // Replace with your database driver
        dataSource.setUrl('jdbc:mysql://localhost:3306/your_database') // Replace with your database URL
        dataSource.setUsername('your_username') // Replace with your database username
        dataSource.setPassword('your_password') // Replace with your database password

        return dataSource
    }

    /**
     * Creates a new Sql object with a connection from the database connection pool
     *
     * @param dataSource - Database connection pool
     * @return Sql - Sql object with a connection from the pool
     */
    Sql createSql(DataSource dataSource) {
        try {
            def connection = dataSource.getConnection()
            return new Sql(connection)
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create Sql object", e)
        }
    }

    /**
     * Closes the Sql object and releases the connection back to the pool
     *
     * @param sql - Sql object to close
     */
    void closeSql(Sql sql) {
        InvokerHelper.invokeMethod(sql, 'close', null)
    }
}
