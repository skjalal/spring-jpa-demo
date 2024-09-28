package com.example.config;

import com.example.interceptors.HibernateDataInterceptor;
import com.example.listerners.HibernateListener;
import jakarta.persistence.EntityManagerFactory;
import java.util.Map;
import org.hibernate.Interceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class HibernateConfig {

  @Bean
  public HibernateListener hibernateListener(EntityManagerFactory factory) {
    return new HibernateListener(factory);
  }

  @Bean
  public HibernatePropertiesCustomizer customizer(Environment env) {
    var customizer = new HibernatePropertiesCustomizerImpl(env);
    return customizer::customize;
  }

  private record HibernatePropertiesCustomizerImpl(Environment env) {

    public void customize(Map<String, Object> hibernateProperties) {
      Interceptor hibernateInterceptor = new HibernateDataInterceptor(env);
      hibernateProperties.put("hibernate.session_factory.interceptor", hibernateInterceptor);
    }
  }
}
