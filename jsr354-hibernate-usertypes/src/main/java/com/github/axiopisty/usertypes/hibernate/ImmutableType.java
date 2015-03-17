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
  public Class returnedClass() {
    return typeClass;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    return x == y || (x != null && y != null && x.equals(y));
  }

  @Override
  public int hashCode(Object o) throws HibernateException {
    return o == null ? 0 : o.hashCode();
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
    throw new UnsupportedOperationException(String.format("%s is immutable", typeClass.getCanonicalName()));
  }

  @Override
  public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
    return (Serializable)value;
  }

  @Override
  public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
    return cached;
  }

  @Override
  public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException {
    return original;
  }

}
