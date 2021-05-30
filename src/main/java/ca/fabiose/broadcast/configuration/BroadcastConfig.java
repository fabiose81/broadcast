package ca.fabiose.broadcast.configuration;

import ca.fabiose.broadcast.domain.Broadcast;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableSwagger2
public class BroadcastConfig {

    @Bean
    public List<Broadcast> loadBrodcastList() throws IOException {
        File file = ResourceUtils.getFile("classpath:static/assets.csv");
        List broadcastList = new CsvToBeanBuilder(new FileReader(file))
                .withType(Broadcast.class)
                .withSkipLines(1)
                .build()
                .parse();
        return broadcastList;
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ca.fabiose.broadcast.controller")
                ).build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Broadcast API")
                .description("Springboot API just for fun")
                .termsOfServiceUrl("https://github.com/fabiose81")
                .contact(new Contact("Fabio dos Santos Estrela","https://github.com/fabiose81","fabiose@gmail.com"))
                .licenseUrl("fabiose@gmail.com").version("1.0").build();
    }
}