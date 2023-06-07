package io.github.gdtknight.smartstore.core.service;

import io.github.gdtknight.smartstore.core.domain.Customer;
import io.github.gdtknight.smartstore.core.domain.CustomerDto;
import io.github.gdtknight.smartstore.core.domain.CustomerGroupDto;
import io.github.gdtknight.smartstore.core.manager.CustomerManager;
import io.github.gdtknight.smartstore.enums.CustomerType;
import io.github.gdtknight.smartstore.enums.SortBy;
import io.github.gdtknight.smartstore.enums.SortOrder;
import io.github.gdtknight.smartstore.exceptions.AppException;
import java.util.Arrays;

/**
 * 고객 정보 관리 서비스 제공 클래스
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class CustomerService {
  private static CustomerService customerService = new CustomerService();
  private static final CustomerManager customerManager = CustomerManager.getInstance();
  private static final CustomerGroupService customerGroupService =
      CustomerGroupService.getInstance();

  /////////////////////////////////////////////////////////
  // 요청 사항 처리를 위한 캐싱 역할 변수들
  private static CustomerGroupDto[] customerGroupDtos = customerGroupService.findAll();
  private static final CustomerDto[][] classifiedCustomerDTOs =
      new CustomerDto[customerGroupDtos.length][];

  public static CustomerService getInstance() {
    if (customerService == null) {
      customerService = new CustomerService();
    }
    return customerService;
  }

  /**
   * @param customerDTO 고객 정보
   * @throws AppException 데이터베이스 오류
   */
  public void save(CustomerDto customerDTO) throws AppException {
    Customer customer = new Customer(customerDTO);
    classifyCustomer(customer);
    customerManager.save(customer);
  }

  /**
   * @return 모든 고객 정보
   * @throws AppException 데이터베이스 오류
   */
  public CustomerDto[] findAll() throws AppException {
    return Arrays.stream(customerManager.selectAll())
        .map(CustomerDto::from)
        .toArray(CustomerDto[]::new);
  }

  public int getNumberOfCustomers() {
    return customerManager.size();
  }

  /**
   * @param id 업데이트 할 고객정보 관리번호
   * @param customerDTO 업데이트할 고객정보
   * @throws AppException 데이터베이스 오류
   */
  public void updateCustomerById(Long id, CustomerDto customerDTO) throws AppException {
    Customer customer = customerManager.findById(id);

    if (customerDTO.name() != null) {
      customer.setName(customerDTO.name());
    }
    if (customerDTO.userId() != null) {
      customer.setUserId(customerDTO.userId());
    }
    if (customerDTO.spentTime() != null) {
      customer.setSpentTime(customerDTO.spentTime());
    }
    if (customerDTO.payAmount() != null) {
      customer.setPayAmount(customerDTO.payAmount());
    }

    classifyCustomer(customer);

    customerManager.save(customer);
  }

  /**
   * 고객 관리 번호를 통한 조회 및 삭제
   *
   * @param id 고객 관리 번호
   */
  public void deleteCustomerById(Long id) {
    customerManager.deleteById(id);
  }

  /**
   * 고객정보를 바탕으로 고객 분류
   *
   * @param customer 고객정보
   */
  public void classifyCustomer(Customer customer) {
    for (CustomerGroupDto customerGroupDTO : customerGroupDtos) {
      if (customerGroupDTO.parameter() == null
          || customerGroupDTO.parameter().getMinPayAmount() == null
          || customerGroupDTO.parameter().getMinSpentTime() == null) {
        continue;
      }
      if (customer.getSpentTime() == null || customer.getPayAmount() == null) {
        customer.setCustomerType(CustomerType.NONE);
      } else {
        if (customerGroupDTO.parameter().getMinSpentTime() <= customer.getSpentTime()
            && customerGroupDTO.parameter().getMinPayAmount() <= customer.getPayAmount()) {
          customer.setCustomerType(customerGroupDTO.customerType());
        }
      }
    }
    if (customer.getCustomerType() == null) {
      customer.setCustomerType(CustomerType.NONE);
    }
  }

  /** 모든 고객 분류 */
  public void classifyAllCustomers() {
    customerGroupDtos = customerGroupService.findAll();
    Customer[] customers = customerManager.selectAll();

    for (Customer customer : customers) {
      classifyCustomer(customer);
      customerManager.save(customer);
    }
  }

  /**
   * 고객 분류 결과를 기준에 따라 정렬한 결과를 뷰로 전달
   *
   * @param sortBy 정렬기준
   * @param sortOrder 정렬순서 (오름차순, 내림차순)
   */
  public CustomerDto[][] getClassifiedCustomerData(SortBy sortBy, SortOrder sortOrder) {
    classifyAllCustomers();
    switch (sortBy) {
      case NAME -> groupingCustomersByCustomerTypeSortByName(sortOrder);
      case SPENT_TIME -> groupingCustomersByCustomerTypeSortBySpentTime(sortOrder);
      case PAY_AMOUNT -> groupingCustomersByCustomerTypeSortByPayAmount(sortOrder);
    }

    return classifiedCustomerDTOs;
  }

  /**
   * 고객 분류 결과를 이름순으로 정렬.
   *
   * @param sortOrder 정렬순서 (오름차순, 내림차순)
   */
  private void groupingCustomersByCustomerTypeSortByName(SortOrder sortOrder) {
    for (int gIdx = 0; gIdx < customerGroupDtos.length; gIdx++) {
      if (sortOrder == SortOrder.ASCENDING) {
        classifiedCustomerDTOs[gIdx] =
            Arrays.stream(
                    customerManager.selectByCustomerTypeOrderByNameAsc(
                        customerGroupDtos[gIdx].customerType()))
                .map(CustomerDto::from)
                .toArray(CustomerDto[]::new);
      } else {
        classifiedCustomerDTOs[gIdx] =
            Arrays.stream(
                    customerManager.selectByCustomerTypeOrderByNameDesc(
                        customerGroupDtos[gIdx].customerType()))
                .map(CustomerDto::from)
                .toArray(CustomerDto[]::new);
      }
    }
  }

  /**
   * 고객 분류 결과를 이용시간순으로 정렬.
   *
   * @param sortOrder 정렬순서 (오름차순, 내림차순)
   */
  private void groupingCustomersByCustomerTypeSortBySpentTime(SortOrder sortOrder) {
    for (int gIdx = 0; gIdx < customerGroupDtos.length; gIdx++) {
      if (sortOrder == SortOrder.ASCENDING) {
        classifiedCustomerDTOs[gIdx] =
            Arrays.stream(
                    customerManager.selectByCustomerTypeOrderBySpentTimeAsc(
                        customerGroupDtos[gIdx].customerType()))
                .map(CustomerDto::from)
                .toArray(CustomerDto[]::new);
      } else {
        classifiedCustomerDTOs[gIdx] =
            Arrays.stream(
                    customerManager.selectByCustomerTypeOrderBySpentTimeDesc(
                        customerGroupDtos[gIdx].customerType()))
                .map(CustomerDto::from)
                .toArray(CustomerDto[]::new);
      }
    }
  }

  /**
   * 고객 분류 결과를 사용금액순으로 정렬.
   *
   * @param sortOrder 정렬순서 (오름차순, 내림차순)
   */
  private void groupingCustomersByCustomerTypeSortByPayAmount(SortOrder sortOrder) {
    for (int gIdx = 0; gIdx < customerGroupDtos.length; gIdx++) {
      if (sortOrder == SortOrder.ASCENDING) {
        classifiedCustomerDTOs[gIdx] =
            Arrays.stream(
                    customerManager.selectByCustomerTypeOrderByPayAmountAsc(
                        customerGroupDtos[gIdx].customerType()))
                .map(CustomerDto::from)
                .toArray(CustomerDto[]::new);
      } else {
        classifiedCustomerDTOs[gIdx] =
            Arrays.stream(
                    customerManager.selectByCustomerTypeOrderByPayAmountDesc(
                        customerGroupDtos[gIdx].customerType()))
                .map(CustomerDto::from)
                .toArray(CustomerDto[]::new);
      }
    }
  }
}
