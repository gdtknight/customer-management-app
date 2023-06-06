package io.github.gdtknight.smartstore.exceptions;

/**
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public enum AppErrorCode {
  UNKNOWN_TYPE("Can't identify customer type"),
  NOT_EXIST_CUSTOMER("Customer doesn't exist."),
  NO_MATCHING_ID("No matching id."),
  NO_MATCHING_GROUP("No matching group."),
  NO_CUSTOMER("No Customers. Please input one first."),
  CANT_SORT("Elements in Array has null. Array can't be sorted."),
  NULL_INPUT("Null Input. Please input something."),
  EMPTY_INPUT("Empty Input. Please input something."),
  INVALID_INPUT("Invalid Input. Please try again."),
  INVALID_TYPE("Invalid Type for Input. Please try again."),
  INVALID_FORMAT("Invalid Format for Input. Please try again."),
  GROUP_ALREADY_SET("group already exists."),
  NO_PARAMETER("No parameter. Set the parameter first"),
  INTERNAL_ERROR("Internal Error."),
  INPUT_END("END is pressed. Exit this menu."),
  END_MSG("END");

  private final String message;

  AppErrorCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
