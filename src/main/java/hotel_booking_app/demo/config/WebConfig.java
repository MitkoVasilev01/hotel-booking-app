package hotel_booking_app.demo.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {

            Path uploadDir = Paths.get("hotel-photos");
            String uploadPath = uploadDir.toFile().getAbsolutePath();

            registry.addResourceHandler("/photos/**")
                    .addResourceLocations("file:/" + uploadPath + "/");
        }
    }

