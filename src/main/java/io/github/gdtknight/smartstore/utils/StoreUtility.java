package io.github.gdtknight.smartstore.utils;

import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.INVALID_FORMAT;

import io.github.gdtknight.smartstore.enums.CustomerType;
import io.github.gdtknight.smartstore.enums.SortOrder;
import io.github.gdtknight.smartstore.exceptions.AppException;

/**
 * 스마트스토어에 필요한 부가 기능 제공
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class StoreUtility {
  public static CustomerType convertInputStrToCustomerType(String input) throws AppException {
    try {
      return CustomerType.of(input);
    } catch (RuntimeException e) {
      throw new AppException(INVALID_FORMAT);
    }
  }

  public static SortOrder convertInputStrToSortOrder(String input) throws AppException {
    try {
      return SortOrder.valueOf(input.toUpperCase()).replaceAbbreviation();
    } catch (IllegalArgumentException e) {
      throw new AppException(INVALID_FORMAT);
    }
  }
}
