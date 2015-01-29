package org.fervorseed.ikeframework.initializer.config.common;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;


@Configuration
public class DataSourceConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUserName;
    @Value("${jdbc.password}")
    private String jdbcPassword;
	
	private final static String JDBC_CONFIG_PATH = "config/datasource_config.xml";
	
	/**
	 * {@link org.springframework.beans.factory.config.PropertyPlaceholderConfigurer}
	 * 
	 * jdbc 설정 파일을 읽어들인다. 
	 * 다른 빈들이 사용하는 프로퍼티들을 로딩 하기때문에 static 선언
	 * */
	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer pp = new PropertyPlaceholderConfigurer();
        pp.setLocations(new Resource[]{ new ClassPathResource(JDBC_CONFIG_PATH)});
        return pp;
	}
	
	/**
     * {@link javax.sql.DataSource}를 빈으로 등록한다.
     *
     * BoneCP는 오픈소스 JDBC Pool 라이브러리이다.
     * 같은 일을 하는 라이브러리로 Tomcat JDBC Pool(Apache DBCP), c3p0 등이 있다.
     */
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(jdbcUserName);
        dataSource.setPassword(jdbcPassword);

        return dataSource;
    }
}