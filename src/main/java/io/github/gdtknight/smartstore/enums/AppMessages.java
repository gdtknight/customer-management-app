package io.github.gdtknight.smartstore.enums;

/**
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public enum AppMessages {
  // Customer Sub Menu
  INPUT_NUMBER_OF_CUSTOMERS("How many customers to input?"),
  INPUT_CUSTOMER_NAME("Input Customer's Name:"),
  INPUT_CUSTOMER_ID("Input Customer's ID:"),
  INPUT_CUSTOMER_SPENT_TIME("Input Customer's Spent Time:"),
  INPUT_CUSTOMER_PAY_AMOUNT("Input Customer's Total Pay Amount:"),

  // Parameter Sub Menu Message
  INPUT_MINIMUM_SPENT_TIME_MSG("Input Minimum Spent Time."),
  INPUT_MINIMUM_PAY_AMOUNT_MSG("Input Minimum Total Pay Amount."),

  // Customer Group Menu Message
  INPUT_CUSTOMER_GROUP_MSG("Which group (GENERAL (G), VIP (V), VVIP (VV))?"),

  INPUT_SORT_ORDER("Which order (ASCENDING (A), DESCENDING (D))?"),

  PRESS_END_MSG("** Press 'end', if you want to exit! **");

  private final String detailMessage;

  AppMessages(String detailMessage) {
    this.detailMessage = detailMessage;
  }

  @Override
  public String toString() {
    return detailMessage;
  }
}
