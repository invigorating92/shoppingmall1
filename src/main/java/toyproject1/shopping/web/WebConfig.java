package toyproject1.shopping.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toyproject1.shopping.web.filter.LogFilter;
import toyproject1.shopping.web.filter.LoginCheckFilter;
import toyproject1.shopping.web.filter.MemberInfoCheckFilter;
import toyproject1.shopping.web.interceptor.LogInterceptor;
import toyproject1.shopping.web.interceptor.LoginCheckInterceptor;
import toyproject1.shopping.web.interceptor.MemberInfoCheckInterceptor;
import toyproject1.shopping.web.interceptor.MemberInfoCheckSessionInterceptor;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/logout","/members/agree", "/members/add","/css/**", "/*.ico", "/error");

        registry.addInterceptor(new MemberInfoCheckInterceptor())
                .order(3)
                .addPathPatterns("/members/info");

        registry.addInterceptor(new MemberInfoCheckSessionInterceptor())
                .order(4)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/info", "/members/info/modify/password", "/address/add",
                        "/login", "/","/members/agree", "/members/add");
    }

//    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*"); //모든 URL 적용

        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*"); //모든 URL 적용

        return filterRegistrationBean;
    }

}
