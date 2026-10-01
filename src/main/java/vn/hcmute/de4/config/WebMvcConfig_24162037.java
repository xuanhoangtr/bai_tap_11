package vn.hcmute.de4.config;

import java.nio.file.Path;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfig_24162037 implements WebMvcConfigurer {
    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor_24162037()).addPathPatterns("/cart/**", "/checkout/**", "/orders/**");
        registry.addInterceptor(new AdminInterceptor_24162037()).addPathPatterns("/admin/**");
    }

    @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path productImages = Path.of(System.getProperty("user.dir"), "uploads", "products").toAbsolutePath().normalize();
        String location = productImages.toUri().toString();
        if (!location.endsWith("/")) location += "/";
        registry.addResourceHandler("/product-images/**").addResourceLocations(location);
    }
}
