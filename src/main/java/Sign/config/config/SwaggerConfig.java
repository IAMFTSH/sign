package Sign.config.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 邝明山
 * @since 2020-05-27
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
//http://localhost:9999/doc.html
//http://localhost:9999/swagger-ui.html
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authentication";

    @Value("${server.port}")
    private String servePort;

    @Bean
    public Docket docket(Environment environment){
        //dev环境和test环境
        Profiles profiles=Profiles.of("8001","8011","8888");
        boolean flag=environment.acceptsProfiles(profiles);;
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name(AUTHORIZATION_HEADER).description("JWT验证")//Token 以及Authorization 为自定义的参数，session保存的名字是哪个就可以写成那个
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("register")
                .enable(flag)   //是否启用Swagger  默认启用
                .select()
                //basePackage需要扫描的包，默认还会会扫描错误页面的controller和容器内的Controller
                //any全部，   没实验
                // none不扫描，   没实验
                // withClassAnnotation扫描类上的注解（arg是射对象）   没实验
                //withMethodAnnotation扫描方法的注解  懒得管了
                .apis(RequestHandlerSelectors.basePackage("graduation.project.controller"))
                //过滤  只扫描请求路径中符合的
/*                .paths(PathSelectors.ant("/user/**"))*/
                .build()
                .globalOperationParameters(pars);
    }
    private ApiInfo apiInfo(){
        Contact contact=new Contact("FTSH","https://www.hao123.com/","2244456@qq.com");
        return  new ApiInfo(
                servePort,
                "description",
                "version",
                "https://www.baidu.com/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
