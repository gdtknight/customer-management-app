package io.github.gdtknight.smartstore.core.manager;

import io.github.gdtknight.smartstore.core.domain.CustomerGroup;
import io.github.gdtknight.smartstore.core.domain.Parameter;
import io.github.gdtknight.smartstore.enums.CustomerType;
import java.util.Arrays;

/**
 * 스마트스토어의 모든 고객 그룹, 세분화 기준 저장 (Database 대체용)
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class CustomerGroupManager {
  private static CustomerGroupManager customerGroupManager = new CustomerGroupManager();
  private final CustomerGroup[] customerGroups;

  private CustomerGroupManager() {
    customerGroups =
        Arrays.stream(CustomerType.values())
            .map(customerType -> new CustomerGroup(customerType, null))
            .toArray(CustomerGroup[]::new);
  }

  public static CustomerGroupManager getInstance() {
    if (customerGroupManager == null) {
      customerGroupManager = new CustomerGroupManager();
    }
    return customerGroupManager;
  }

  /**
   * 고객 그룹 저장
   *
   * @param customerGroup 고객 그룹
   */
  public void save(CustomerGroup customerGroup) {
    Arrays.stream(customerGroups)
        .filter(group -> group.getCustomerType() == customerGroup.getCustomerType())
        .forEach(group -> group.setParameter(customerGroup.getParameter()));
  }

  /**
   * 고객 그룹 저장
   *
   * @param customerType 고객 유형
   * @param parameter 분류 기준
   */
  public void save(CustomerType customerType, Parameter parameter) {
    Arrays.stream(customerGroups)
        .filter(group -> group.getCustomerType() == customerType)
        .forEach(group -> group.setParameter(parameter));
  }

  /**
   * @param customerType 고객 유형
   * @return 해당 고객 그룹
   */
  public CustomerGroup selectCustomerGroupByCustomerType(CustomerType customerType) {
    return Arrays.stream(customerGroups)
        .filter(customerGroup -> customerGroup.getCustomerType() == customerType)
        .findFirst()
        .orElse(null);
  }

  /**
   * @return 모든 고객 그룹
   */
  public CustomerGroup[] selectAllCustomerGroup() {
    return Arrays.stream(customerGroups).toArray(CustomerGroup[]::new);
  }
}
