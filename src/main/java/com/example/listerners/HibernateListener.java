package com.example.listerners;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.lang.NonNull;

@Slf4j
@RequiredArgsConstructor
public class HibernateListener implements PreInsertEventListener, PreUpdateEventListener,
    PostLoadEventListener {

  private final EntityManagerFactory factory;

  @PostConstruct
  public void init() {
    SessionFactoryImpl sessionFactory = factory.unwrap(SessionFactoryImpl.class);
    EventListenerRegistry registry = sessionFactory.getServiceRegistry()
        .getService(EventListenerRegistry.class);
    Optional.ofNullable(registry).ifPresent(this::setUpRegistry);
  }

  @Override
  public boolean onPreInsert(PreInsertEvent preInsertEvent) {
    log.info("onPreInsert(PreInsertEvent) === {}", preInsertEvent);
    return false;
  }

  @Override
  public void onPostLoad(PostLoadEvent postLoadEvent) {
    log.info("onPostLoad(PostLoadEvent) === {}", postLoadEvent);
  }

  @Override
  public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
    log.info("onPreUpdate(PreUpdateEvent) === {}", preUpdateEvent);
    return false;
  }

  private void setUpRegistry(@NonNull EventListenerRegistry registry) {
    registry.appendListeners(EventType.PRE_INSERT, this);
    registry.appendListeners(EventType.PRE_UPDATE, this);
    registry.appendListeners(EventType.POST_LOAD, this);
  }
}
