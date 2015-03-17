package com.github.axiopisty.usertypes.hibernate.jsr354;

import com.github.axiopisty.usertypes.hibernate.ImmutableType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.UserType;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *   A hibernate {@link CompositeUserType} for {@code javax.money.MonetaryAmount} defined in
 *   <a href="https://jcp.org/en/jsr/detail?id=354">JSR-354</a>.
 * </p>
 *
 * @author Elliot Huntington
 *
 * @see UserType
 */
public class MonetaryAmountType extends ImmutableType<MonetaryAmount> implements CompositeUserType {

  private final static Type[] PROPERTY_TYPES = { BigDecimalType.INSTANCE, StringType.INSTANCE };
  private final static String[] PROPERTY_NAMES = {"number", "currency"};

  public MonetaryAmountType() {
    super(MonetaryAmount.class);
  }

  @Override
  public Type[] getPropertyTypes() {
    return PROPERTY_TYPES;
  }

  @Override
  public String[] getPropertyNames() {
    return PROPERTY_NAMES;
  }

  @Override
  public Object getPropertyValue(Object component, int property) throws HibernateException {
    MonetaryAmount amount = (MonetaryAmount) component;
    switch(property) {
      case 0: return amount.getNumber();
      case 1: return amount.getCurrency();
      default: throw new IllegalArgumentException(property + " is not a valid property index");
    }
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor sessionImplementor, Object owner) throws HibernateException, SQLException {
    if(names.length != PROPERTY_NAMES.length) {
      throw new HibernateException("Expected " + PROPERTY_NAMES.length + " column names but got " + names.length);
    }
    final BigDecimal number = rs.getBigDecimal(names[0]);
    final String currencyCode = rs.getString(names[1]);

    final MonetaryAmount amount;
    if(number == null ^ currencyCode == null) {
      throw new HibernateException("both the value and the currency must be set, or both must be null");
    } else if (number == null) {
      amount = null;
    } else {
      amount = Money.of(number, MonetaryCurrencies.getCurrency(currencyCode));
    }
    return amount;
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
    final BigDecimal amount;
    final String currencyCode;
    if(value == null) {
      amount = null;
      currencyCode = null;
    } else {
      final MonetaryAmount monetaryAmount = (MonetaryAmount)value;
      amount = monetaryAmount.getNumber().numberValue(BigDecimal.class);
      currencyCode = monetaryAmount.getCurrency().getCurrencyCode();
    }
    st.setBigDecimal(index, amount);
    st.setString(index + 1, currencyCode);
  }
}
