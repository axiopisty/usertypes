package com.github.axiopisty.usertypes.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;

/**
 * @author Elliot Huntington
 */
public abstract class ImmutableType<T> implements CompositeUserType {

  protected final Class<T> typeClass;

  protected ImmutableType(Class<T> typeClass) {
    this.typeClass = typeClass;
  }

  @Override
  public final Class returnedClass() {
    return typeClass;
  }

  @Override
  public final boolean equals(Object x, Object y) throws HibernateException {
    return x == y || (x != null && y != null && x.equals(y));
  }

  @Override
  public final int hashCode(Object o) throws HibernateException {
    return o == null ? 0 : o.hashCode();
  }

  @Override
  public final Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public final boolean isMutable() {
    return false;
  }

  @Override
  public final void setPropertyValue(Object component, int property, Object value) throws HibernateException {
    throw new UnsupportedOperationException(String.format("%s is immutable", typeClass.getCanonicalName()));
  }

  @Override
  public final Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
    return (Serializable)value;
  }

  @Override
  public final Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
    return cached;
  }

  @Override
  public final Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException {
    return original;
  }

}
