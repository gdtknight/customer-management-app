package io.github.gdtknight.smartstore.core.domain;

import io.github.gdtknight.smartstore.enums.CustomerType;

/**
 * 고객 정보 저장을 위한 엔티티 클래스
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class Customer {
  /** 관리 번호 부여용 */
  private static Long seqNo = 0L;

  private final Long id; // 고객 관리 번호
  private String name;
  private String userId;
  private Integer spentTime;
  private Integer payAmount;
  private CustomerType customerType;

  public Customer(String name, String userId, Integer spentTime, Integer payAmount) {
    synchronized (this) {
      this.id = seqNo++;
    }
    this.name = name;
    this.userId = userId;
    this.spentTime = spentTime;
    this.payAmount = payAmount;
    this.customerType = null;
  }

  public Customer(CustomerDto dto) {
    synchronized (this) {
      this.id = dto.id() == null ? seqNo++ : dto.id();
    }
    this.name = dto.name();
    this.userId = dto.userId();
    this.spentTime = dto.spentTime();
    this.payAmount = dto.payAmount();
    this.customerType = null;
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public Integer getSpentTime() {
    return spentTime;
  }

  public Integer getPayAmount() {
    return payAmount;
  }

  public CustomerType getCustomerType() {
    return customerType;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSpentTime(int spentTime) {
    this.spentTime = spentTime;
  }

  public void setPayAmount(int payAmount) {
    this.payAmount = payAmount;
  }

  public void setCustomerType(CustomerType customerType) {
    this.customerType = customerType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Customer customer = (Customer) o;

    return getId().equals(customer.getId());
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }
}
