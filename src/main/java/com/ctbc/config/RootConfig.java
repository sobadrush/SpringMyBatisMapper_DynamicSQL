package com.ctbc.config;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DriverManagerDataSource;

@Configuration
@PropertySource(value = { "classpath:database.properties" }, encoding = "utf-8")
@ComponentScan(basePackages = { "com.ctbc.model.dao" /* , "com.ctbc.mapper" */ })
@MapperScan(basePackages = { "com.ctbc.mapper" })
public class RootConfig {

	@Bean /* (destroyMethod = "close") */
	public DataSource c3p0DataSource(
			@Value("${jdbc.sqlServer.driverClassName}") String driverClassName,
			@Value("${jdbc.sqlServer.jdbcUrl}") String jdbcUrl,
			@Value("${jdbc.sqlServer.user}") String user,
			@Value("${jdbc.sqlServer.password}") String password) {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(driverClassName);
			ds.setJdbcUrl(jdbcUrl);
			ds.setUser(user);
			ds.setPassword(password);
			ds.setAcquireIncrement(5);
			ds.setIdleConnectionTestPeriod(60);
			ds.setMaxPoolSize(100);
			ds.setMaxStatements(50);
			ds.setMinPoolSize(10);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return ds;
	}

	@Bean
	public DataSource driverManagerDS(
			@Value("${jdbc.sqlServer.driverClassName}") String driverClassName,
			@Value("${jdbc.sqlServer.jdbcUrl}") String jdbcUrl,
			@Value("${jdbc.sqlServer.user}") String user,
			@Value("${jdbc.sqlServer.password}") String password) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClass(driverClassName);
		ds.setJdbcUrl(jdbcUrl);
		ds.setUser(user);
		ds.setPassword(password);
		return ds;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("driverManagerDS") DataSource ds, ApplicationContext applicationContext) throws Exception {
		System.out.println("啟用連線池：" + ds);
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(ds);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:XML_Mappers/*.xml"));
		sqlSessionFactory.setTypeAliasesPackage("com.ctbc.model.vo");
		return sqlSessionFactory.getObject();
	}

//	@Bean // -- 可用 @MapperScan(basePackages = { "com.ctbc.mapper" })
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		MapperScannerConfigurer scanner = new MapperScannerConfigurer();
//		scanner.setBasePackage("com.ctbc.mapper");
//		scanner.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");// http://www.mybatis.org/spring/zh/mappers.html
//		return scanner;
//	}

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

		/******** 測試連線 **********/
		DataSource ds = context.getBean("c3p0DataSource", DataSource.class);
//		DataSource ds = context.getBean("driverManagerDS", DataSource.class);
		try {
			Connection conn = ds.getConnection();
			DatabaseMetaData dsMeta = conn.getMetaData();
			System.out.println("dsMeta.getDatabaseProductName() --- " + dsMeta.getDatabaseProductName());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ds = null;
		}
		/**************************/
		context.close();
	}

}
