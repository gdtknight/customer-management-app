package io.github.gdtknight.smartstore.core.service;

import io.github.gdtknight.smartstore.core.domain.CustomerGroup;
import io.github.gdtknight.smartstore.core.domain.CustomerGroupDto;
import io.github.gdtknight.smartstore.core.domain.Parameter;
import io.github.gdtknight.smartstore.core.manager.CustomerGroupManager;
import io.github.gdtknight.smartstore.enums.CustomerType;
import io.github.gdtknight.smartstore.exceptions.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.NO_MATCHING_GROUP;
import static org.junit.jupiter.api.Assertions.*;

class CustomerGroupServiceTest {
  private final CustomerGroupService sut = CustomerGroupService.getInstance();
  private final CustomerGroupManager customerGroupManager = CustomerGroupManager.getInstance();

  @DisplayName("[UPDATE] 업데이트할 고객 그룹과 파라미터를 전달하면 업데이트 후 저장")
  @Test
  void givenDtoAndParameter_whenUpdateGroup_thenSaveUpdatedGroup() {
    // Given
    Parameter testParameter = new Parameter(123, 123456);

    // When
    sut.updateGroup(CustomerType.GENERAL, testParameter);
    CustomerGroupDto actual = sut.find(CustomerType.GENERAL);

    // Then
    assertEquals(123, actual.parameter().getMinSpentTime());
    assertEquals(123456, actual.parameter().getMinPayAmount());
  }

  @DisplayName("[FIND] 고객 유형을 전달하면 일치하는 그룹 DTO를 반환.")
  @ParameterizedTest(name = "고객 유형 (CustomerType): {0}")
  @EnumSource(CustomerType.class)
  void givenCustomerType_whenFind_thenReturnsCustomerGroupDto(CustomerType customerType) {
    // Given
    CustomerGroup customerGroup =
        customerGroupManager.selectCustomerGroupByCustomerType(customerType);
    CustomerGroupDto expected = CustomerGroupDto.fromEntity(customerGroup);

    // When
    CustomerGroupDto actual = sut.find(customerType);

    // Then
    assertEquals(expected.customerType(), actual.customerType());
  }

  @DisplayName("[FIND] 아무것도 전달하지 않으면 NO_MATCHING_GROUP 예외 발생")
  @Test
  void givenNothing_whenFind_thenThrowsStoreException() {
    // Given

    // When & Then
    AppException exception = assertThrows(AppException.class, () -> sut.find(null));
    assertEquals(NO_MATCHING_GROUP.getMessage(), exception.getMessage());
  }

  @DisplayName("[FIND ALL] 함수 호출시 모든 고객 그룹을 배열에 담아 반환")
  @Test
  void givenNothing_whenFindAll_thenReturnsCustomerGroupDtoArray() {
    // Given
    CustomerGroup[] customerGroups = customerGroupManager.selectAllCustomerGroup();
    CustomerGroupDto[] expected =
        Arrays.stream(customerGroups)
            .map(CustomerGroupDto::fromEntity)
            .toArray(CustomerGroupDto[]::new);

    // When
    CustomerGroupDto[] actual = sut.findAll();

    // Then
    assertArrayEquals(expected, actual);
  }
}
