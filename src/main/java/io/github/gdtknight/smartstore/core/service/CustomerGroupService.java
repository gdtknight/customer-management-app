package io.github.gdtknight.smartstore.core.service;

import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.NO_MATCHING_GROUP;

import io.github.gdtknight.smartstore.core.domain.CustomerGroup;
import io.github.gdtknight.smartstore.core.domain.CustomerGroupDto;
import io.github.gdtknight.smartstore.core.domain.Parameter;
import io.github.gdtknight.smartstore.core.manager.CustomerGroupManager;
import io.github.gdtknight.smartstore.enums.CustomerType;
import io.github.gdtknight.smartstore.exceptions.AppException;
import java.util.Arrays;

/**
 * 고객 유형 관리 서비스 제공 클래스
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class CustomerGroupService {
  private static CustomerGroupService customerGroupService = new CustomerGroupService();
  private final CustomerGroupManager customerGroupManager = CustomerGroupManager.getInstance();
  private final CustomerService customerService = CustomerService.getInstance();

  public static CustomerGroupService getInstance() {
    if (customerGroupService == null) {
      customerGroupService = new CustomerGroupService();
    }
    return customerGroupService;
  }

  /**
   * 그룹 파라미터 설정 후 저장
   *
   * @param customerType 고객 유형
   * @param parameter 분류 기준
   */
  public void updateGroup(CustomerType customerType, Parameter parameter) {
    if (customerType == null || parameter == null) return;
    customerGroupManager.save(customerType, parameter);
    customerService.classifyAllCustomers();
  }

  /**
   * @param customerType 고객 유형
   * @return 고객 유형과 일치하는 그룹
   * @throws AppException 데이터베이스에 일치하는 그룹이 없을 경우
   */
  public CustomerGroupDto find(CustomerType customerType) throws AppException {
    CustomerGroup customerGroup =
        customerGroupManager.selectCustomerGroupByCustomerType(customerType);

    if (customerGroup == null) throw new AppException(NO_MATCHING_GROUP);

    return CustomerGroupDto.fromEntity(customerGroup);
  }

  /**
   * @return 데이터베이스에 저장된 모든 고객 그룹
   * @throws AppException 데이터베이스 오류
   */
  public CustomerGroupDto[] findAll() throws AppException {
    return Arrays.stream(customerGroupManager.selectAllCustomerGroup())
        .map(CustomerGroupDto::fromEntity)
        .toArray(CustomerGroupDto[]::new);
  }
}
