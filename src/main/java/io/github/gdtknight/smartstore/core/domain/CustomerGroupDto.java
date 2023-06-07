package io.github.gdtknight.smartstore.core.domain;

import io.github.gdtknight.smartstore.enums.CustomerType;

/**
 * 스마트스토어 애플리케이션 내에서 그룹 정보 전달
 *
 * @param customerType 그룹별 고객 유형
 * @param parameter 그룹별 분류 기준
 */
public record CustomerGroupDto(CustomerType customerType, Parameter parameter) {
  public static CustomerGroupDto fromEntity(CustomerGroup customerGroup) {
    return new CustomerGroupDto(customerGroup.getCustomerType(), customerGroup.getParameter());
  }

  public static CustomerGroup toEntity(CustomerGroupDto customerGroupDto) {
    return new CustomerGroup(customerGroupDto.customerType(), customerGroupDto.parameter());
  }

  public String groupTitle() {
    StringBuilder sb = new StringBuilder();
    sb.append("==============================\n");
    sb.append("Group: ").append(customerType);
    if (parameter == null) {
      sb.append(" ( Time : null, Pay Amount : null )\n");
    } else {
      sb.append(" ( Time :")
          .append(parameter.getMinSpentTime())
          .append(", Pay Amount :")
          .append(parameter.getMinPayAmount())
          .append(" )\n");
    }
    sb.append("==============================");

    return sb.toString();
  }

  @Override
  public String toString() {
    String parameterStr = parameter == null ? "null" : parameter.toString();
    return "CustomerType: " + customerType + "\nParameter: " + parameterStr;
  }
}
