package io.github.gdtknight.smartstore.core.domain;

import io.github.gdtknight.smartstore.enums.CustomerType;

/**
 * 스마트스토어 애플리케이션 내에서 고객 정보 전달
 *
 * @param id 관리 번호
 * @param name 이름
 * @param userId 사용자 ID
 * @param spentTime 이용시간
 * @param payAmount 이용금액
 * @param customerType 고객 유형
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public record CustomerDto(
    Long id,
    String name,
    String userId,
    Integer spentTime,
    Integer payAmount,
    CustomerType customerType) {

  public static CustomerDto from(Customer customer) {
    return new CustomerDto(
        customer.getId(),
        customer.getName(),
        customer.getUserId(),
        customer.getSpentTime(),
        customer.getPayAmount(),
        customer.getCustomerType());
  }

  public static CustomerDto of(
      String customerName,
      String customerUserId,
      Integer customerSpentTime,
      Integer customerPayAmount) {
    return new CustomerDto(
        null, customerName, customerUserId, customerSpentTime, customerPayAmount, null);
  }

  @Override
  public String toString() {
    // Customer{userId='TEST123', name='TEST', spentTime=null, payAmount=null, group=null}
    return "Customer{"
        + "userId='"
        + userId
        + '\''
        + ", name='"
        + name
        + '\''
        + ", spentTime="
        + spentTime
        + ", payAmount="
        + payAmount
        + ", customerType="
        + customerType
        + '}';
  }
}
