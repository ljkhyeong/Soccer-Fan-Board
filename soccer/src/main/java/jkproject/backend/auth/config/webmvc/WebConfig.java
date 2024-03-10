package jkproject.backend.auth.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://13.125.252.116", "https://13.125.252.116",
				"http://ec2-13-125-252-116.ap-northeast-2.compute.amazonaws.com/",
				"https://ec2-13-125-252-116.ap-northeast-2.compute.amazonaws.com/")
			.allowedMethods("OPTION", "GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(3600);
	}
}
