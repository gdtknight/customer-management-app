package io.github.gdtknight.smartstore.enums;

import io.github.gdtknight.smartstore.core.domain.Customer;
import java.util.Comparator;

/**
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public enum SortBy {
  NAME(Comparator.comparing(Customer::getName)),
  SPENT_TIME(Comparator.comparing(Customer::getSpentTime)),
  PAY_AMOUNT(Comparator.comparing(Customer::getPayAmount));

  private final Comparator<Customer> customerComparator;

  SortBy(Comparator<Customer> customerComparator) {
    this.customerComparator = customerComparator;
  }

  public Comparator<Customer> getCustomerComparator() {
    return customerComparator;
  }
}
