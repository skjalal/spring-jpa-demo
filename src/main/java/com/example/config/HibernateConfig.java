package com.example.config;

import com.example.interceptors.HibernateDataInterceptor;
import java.util.Map;
import org.hibernate.Interceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class HibernateConfig {

  @Bean
  public Interceptor hibernateInterceptor(Environment env) {
    return new HibernateDataInterceptor(env);
  }

  @Bean
  public HibernatePropertiesCustomizer customizer(Interceptor hibernateInterceptor) {
    var customizer = new HibernatePropertiesCustomizerImpl(hibernateInterceptor);
    return customizer::customize;
  }

  private record HibernatePropertiesCustomizerImpl(Interceptor hibernateInterceptor) {

    public void customize(Map<String, Object> hibernateProperties) {
      hibernateProperties.put("hibernate.session_factory.interceptor", hibernateInterceptor);
    }
  }
}
