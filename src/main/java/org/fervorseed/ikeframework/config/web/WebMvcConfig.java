package org.fervorseed.ikeframework.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
* @package org.fervorseed.ikeframework.common.config
* @fileName WebMvcConfig.java
* 
* @Company : FervorSeed
* @Author  : SIM
* @Date    : 2015. 1. 24. 오후 5:14:37
* @Version : 1.0
* @Description : \@EnableWebMvc 설정 파일
* 						\@EnableWebMvc 은 xml 설정에서 <mvc:annotation-driven /> 의 역활이다
*/

@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	private static final int CACHE_PERIOD = 31556926; // one year

	/**
	 * HttpMessageConverter 설정.
	 * */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(getJsonMessageConverter());
//		converters.add(xmlMessageConverter());
	}
	
	@Bean
	public StringHttpMessageConverter getStringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter =  new StringHttpMessageConverter();
		return stringHttpMessageConverter;
		
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter getJsonMessageConverter() {
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonMessageConverter.setPrettyPrint(true);
		return jsonMessageConverter;
	}
//	
//	@Bean MappingJackson2HttpMessageConverter xmlMessageConverter() {
//		MappingJackson2HttpMessageConverter xmlMessageConverter = new MappingJackson2HttpMessageConverter();
//		return xmlMessageConverter;
//	}

	/**
	 * resource 파일 경로 설정 (css, javascript 등등)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
		  .addResourceLocations("/resources/")
		  .addResourceLocations("classpath:/resources/")
		  .setCachePeriod(CACHE_PERIOD);	// 브라우저 캐시 만료 기간 1년 설정.
	}
	
	/**
     * <mvc:default-servlet-handler/>
     * 기본서블릿 사용 선언
     */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * 뷰 리졸버 설정.
	 * */
	@Bean
	public  InternalResourceViewResolver getInternalViewResolver() {
		InternalResourceViewResolver internalViewResolver = new InternalResourceViewResolver();
		internalViewResolver.setViewClass(JstlView.class);
		internalViewResolver.setPrefix("/WEB-INF/view/");
		internalViewResolver.setSuffix(".jsp");
		internalViewResolver.setOrder(1);
		return internalViewResolver;
	}
}