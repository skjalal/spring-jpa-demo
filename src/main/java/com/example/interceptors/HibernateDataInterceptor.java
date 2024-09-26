package com.example.interceptors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.core.env.Environment;

@Slf4j
@RequiredArgsConstructor
public class HibernateDataInterceptor implements Interceptor {

  private final Environment env;

  @Override
  public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames,
      Type[] types) throws CallbackException {
    log.info("HibernateDataInterceptor onLoad: {}", env.getActiveProfiles()[0]);
    return Interceptor.super.onLoad(entity, id, state, propertyNames, types);
  }

  @Override
  public boolean onFlushDirty(Object entity, Object id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
    log.info("HibernateDataInterceptor onFlush");
    return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames,
        types);
  }

  @Override
  public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames,
      Type[] types) throws CallbackException {
    log.info("HibernateDataInterceptor onSave");
    return Interceptor.super.onSave(entity, id, state, propertyNames, types);
  }

  @Override
  public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames,
      Type[] types) throws CallbackException {
    log.info("HibernateDataInterceptor onDelete");
    Interceptor.super.onDelete(entity, id, state, propertyNames, types);
  }
}
