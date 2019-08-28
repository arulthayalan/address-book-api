package au.com.pwc.addressbook.swagger;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.PathProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

/**
 * Swagger configuration.
 */
@Configuration
@EnableSwagger2
public class SwaggerAutoConfiguration {

    @Value("${api.title}")
    private String apiTitle;

    @Value("${api.description}")
    private String apiDescription;

    @Value("${api.version}")
    private String apiVersion;

    @Value("${server.servlet.context-path}")
    private String serverContextPath;

    /**
     * @param servletContext application {@link ServletContext} environment properties
     * @return configured Swagger {@link Docket} bean.
     */

    @Bean
    public Docket restApi(ServletContext servletContext) {
        final ApiInfo information = new ApiInfoBuilder()
                .title(apiTitle)
                .description(apiDescription)
                .version(apiVersion)
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("").build();


        return new Docket(DocumentationType.SWAGGER_2).pathProvider(this.pathProvider(servletContext)).apiInfo(information).select()
                .paths(paths()).build();
    }


    private Predicate<String> paths() {
        return or(regex("/addressbook.*"), regex("/addressbook"), regex("/addressbooks"));
    }

    /**
     * @return {@link PathProvider} bean used by Swagger.
     */
    private PathProvider pathProvider(ServletContext servletContext) {
        return new RelativePathProvider(servletContext) {
            @Override
            protected String getDocumentationPath() {
                return "/";
            }

            @Override
            protected String applicationPath() {
                return serverContextPath;
            }

        };
    }

}
