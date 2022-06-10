package com.bcf.employee;

import com.bcf.employee.config.ApplicationConstants;
import com.bcf.employee.config.DefaultProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@SpringBootApplication
public class EmployeeHeirarchyApiApplication implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(EmployeeHeirarchyApiApplication.class);

    private final Environment env;


    @Value("${management.endpoints.web.base-path:/actuator}")
    private String propertiesHealthUrl;

    public EmployeeHeirarchyApiApplication(Environment env) {
        this.env = env;
    }


    private static void logApplicationStartup(Environment env) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        final String port = env.getProperty("server.port");
        String serverPort = isBlank(port) ? "8080" : port;
        String contextPath = env.getProperty("server.servlet.context-path");
        if (isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "SpringDoc: \t{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                protocol + "://" + hostAddress + ":" + serverPort + contextPath + "swagger-ui/index.html",
                env.getActiveProfiles());
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EmployeeHeirarchyApiApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }
    @Override
    public void afterPropertiesSet() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT)
                && activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }
    }
}
