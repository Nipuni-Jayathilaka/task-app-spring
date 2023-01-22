package lk.ijse.dep9.app;

import lk.ijse.dep9.app.api.filter.SecurityFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

@Slf4j
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    public WebAppInitializer() {
        System.out.println("Web app initializer");
        log.debug("spring starting");
    }

    @Override
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy filterProxy=new DelegatingFilterProxy("securityFilter");//can register spring filter only
        return new Filter[]{filterProxy};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebRootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebAppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
