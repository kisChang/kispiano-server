package io.kischang.kispiano.admin.conf;

import com.kischang.simple_utils.web.converter.IntToBaseValEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author KisChang
 * @date 2020-07-01
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new IntToBaseValEnumConverterFactory());
    }
}
