package com.github.axiopisty.usertypes.wrapper.entities;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

import javax.money.MonetaryAmount;
import javax.persistence.*;

/**
 * @author Elliot Huntington
 */
@Entity
@Table(name="monetary_amount_wrapper")
@NamedQueries({
  @NamedQuery(
    name = MonetaryAmountWrapper.QUERIES.DELETE_ALL,
    query = "delete from MonetaryAmountWrapper"
  ),
  @NamedQuery(
    name = MonetaryAmountWrapper.QUERIES.FIND_BY_MONETARY_AMOUNT,
    query = "select maw from MonetaryAmountWrapper maw where maw.monetaryAmount = :monetaryAmount"
  ),
  @NamedQuery(
    name = MonetaryAmountWrapper.QUERIES.FIND_BY_MONETARY_AMOUNT_GT,
    query = "select maw from MonetaryAmountWrapper maw where maw.monetaryAmount.number > :monetaryAmount"
  )
})
public class MonetaryAmountWrapper {

  public static class QUERIES {
    public final static String FIND_BY_MONETARY_AMOUNT = "com.github.axiopisty.usertypes.wrapper.entities.MonetaryAmountWrapper.QUERIES.FIND_BY_MONETARY_AMOUNT";
    public final static String FIND_BY_MONETARY_AMOUNT_GT = "com.github.axiopisty.usertypes.wrapper.entities.MonetaryAmountWrapper.QUERIES.FIND_BY_MONETARY_AMOUNT_GT";
    public final static String DELETE_ALL = "com.github.axiopisty.usertypes.wrapper.entities.MonetaryAmountWrapper.QUERIES.DELETE_ALL";
  }

  @Id
  @GeneratedValue
  private Long id;

  @Type(type = "com.github.axiopisty.usertypes.hibernate.jsr354.MonetaryAmountType")
  @Columns(columns = {
    @Column(name = "monetary_amount"),
    @Column(name = "currency_code")
  })
  private MonetaryAmount monetaryAmount;

  public Long getId() {
    return id;
  }

  public MonetaryAmount getMonetaryAmount() {
    return monetaryAmount;
  }

  public void setMonetaryAmount(MonetaryAmount monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
  }
}
