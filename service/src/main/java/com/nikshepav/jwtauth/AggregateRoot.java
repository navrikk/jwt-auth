package com.nikshepav.jwtauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

@Getter
@MappedSuperclass
public abstract class AggregateRoot<A extends AggregateRoot<A>> extends BaseEntity {

    @JsonIgnore
    @Getter(onMethod = @__(@DomainEvents))
    private transient final List<Object> domainEvents = new ArrayList<>(0);

    protected <T> T registerEvent(T event) {
        Assert.notNull(event, "Domain event must not be null!");
        this.domainEvents.add(event);
        return event;
    }

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @SuppressWarnings("unchecked")
    protected final A andEventsFrom(A aggregate) {
        Assert.notNull(aggregate, "Aggregate must not be null!");
        this.domainEvents.addAll(aggregate.getDomainEvents());
        return (A) this;
    }

    @SuppressWarnings("unchecked")
    protected final A andEvent(Object event) {
        registerEvent(event);
        return (A) this;
    }
}
