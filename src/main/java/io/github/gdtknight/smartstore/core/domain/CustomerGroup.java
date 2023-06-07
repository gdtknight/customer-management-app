package io.github.gdtknight.smartstore.core.domain;

import io.github.gdtknight.smartstore.enums.CustomerType;

/**
 * 고객 유형별 세부 기준 관리를 위한 고객 그룹 엔티티 클래스
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 * @see CustomerType
 * @see Parameter
 */
public class CustomerGroup {
  private final CustomerType customerType;
  private Parameter parameter;

  public CustomerGroup(CustomerType customerType, Parameter parameter) {
    this.customerType = customerType;
    this.parameter = parameter;
  }

  public CustomerType getCustomerType() {
    return this.customerType;
  }

  public Parameter getParameter() {
    return this.parameter;
  }

  public void setParameter(Parameter parameter) {
    if (this.parameter == null) this.parameter = parameter;
    else this.parameter.update(parameter);
  }
}
