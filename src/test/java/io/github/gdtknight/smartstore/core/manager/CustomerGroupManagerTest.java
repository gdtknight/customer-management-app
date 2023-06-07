package io.github.gdtknight.smartstore.core.manager;

import static io.github.gdtknight.smartstore.enums.CustomerType.GENERAL;
import static org.junit.jupiter.api.Assertions.*;

import io.github.gdtknight.smartstore.core.domain.CustomerGroup;
import io.github.gdtknight.smartstore.core.domain.Parameter;
import io.github.gdtknight.smartstore.enums.CustomerType;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class CustomerGroupManagerTest {
  private static final CustomerGroupManager customerGroupManager =
      CustomerGroupManager.getInstance();

  @AfterEach
  void restoreCustomerGroup() {
    // Instance 를 직접 사용하기 때문에 각 테스트가 끝난 이후 직접 초기화 해주어야 각각의 테스트가 의도한대로 수행됨
    // 이 과정이 없을 경우 먼저 수행된 테스트의 결과가 이후 테스트에 영향을 미침.
    Arrays.stream(CustomerType.values())
        .forEach(type -> customerGroupManager.save(new CustomerGroup(type, null)));
  }

  @DisplayName("[SAVE] 고객 그룹 - 저장 성공")
  @Test
  void givenCustomerGroup_whenSaves_thenSavesCustomerGroup() {
    // Given
    Parameter parameter = new Parameter(123, 123456);
    CustomerGroup testGroup = new CustomerGroup(GENERAL, parameter);

    // When
    customerGroupManager.save(testGroup);
    CustomerGroup updated = customerGroupManager.selectCustomerGroupByCustomerType(GENERAL);

    // Then
    assertEquals(GENERAL, updated.getCustomerType());
    assertEquals(parameter, updated.getParameter());
    assertEquals(123, updated.getParameter().getMinSpentTime());
    assertEquals(123456, updated.getParameter().getMinPayAmount());
  }

  @DisplayName("[SAVE] 알 수 없는 고객 그룹 - 저장 실패")
  @ParameterizedTest(name = "고객 유형 (CustomerType): {0}")
  @EnumSource(CustomerType.class)
  void givenUnknownCustomerType_whenSave_thenChangesNothing(CustomerType customerType) {
    // Given
    Parameter parameter = new Parameter(123, 123456);
    CustomerGroup testGroup = new CustomerGroup(null, parameter);

    // When
    customerGroupManager.save(testGroup);
    CustomerGroup actual = customerGroupManager.selectCustomerGroupByCustomerType(customerType);

    // Then
    assertNull(actual.getParameter());
  }

  @DisplayName("[SELECT] 고객 유형별 그룹 조회 - 조회 성공")
  @ParameterizedTest(name = "고객 유형 (CustomerType) : {0}")
  @EnumSource(CustomerType.class)
  void givenCustomerType_whenSelectCustomerGroup_thenReturnsSelectedCustomerGroup(
      CustomerType customerType) {
    // Given

    // Given && When
    CustomerGroup actual = customerGroupManager.selectCustomerGroupByCustomerType(customerType);

    // Then
    assertEquals(customerType, actual.getCustomerType());
  }

  @DisplayName("[SELECT] 고객 유형이 주어지지 않은 경우 - Null")
  @Test
  void givenNothing_whenSelectCustomerGroup_thenReturnsSelectedCustomerGroup() {
    // Given

    // When & Then
    assertNull(customerGroupManager.selectCustomerGroupByCustomerType(null));
  }

  @DisplayName("[SELECT] 모든 고객 그룹 조회")
  @ParameterizedTest(name = "고객 유형 (CustomerType): {0}")
  @EnumSource(CustomerType.class)
  void givenNothing_whenSelectAllCustomerGroup_thenReturnsAllCustomerGroupArray(
      CustomerType customerType) {
    // Given

    // When
    CustomerGroup[] actual = customerGroupManager.selectAllCustomerGroup();

    // Then
    assertTrue(Arrays.stream(actual).anyMatch(group -> group.getCustomerType() == customerType));
  }
}
