
package inf.unideb.hu.exam.system.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for configure.
 */
@Configuration
public class Configurations {

    /**
     * Model mapper bean.
     * @return a new {@link ModelMapper}.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
