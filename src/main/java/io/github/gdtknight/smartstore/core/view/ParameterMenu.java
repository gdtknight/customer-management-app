package io.github.gdtknight.smartstore.core.view;

import static io.github.gdtknight.smartstore.enums.AppMessages.INPUT_CUSTOMER_GROUP_MSG;
import static io.github.gdtknight.smartstore.enums.AppMessages.PRESS_END_MSG;
import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.*;
import static io.github.gdtknight.smartstore.utils.StoreUtility.convertInputStrToCustomerType;

import io.github.gdtknight.smartstore.core.service.CustomerGroupService;
import io.github.gdtknight.smartstore.enums.CustomerType;
import io.github.gdtknight.smartstore.exceptions.AppException;
import io.github.gdtknight.smartstore.utils.ScannerUtility;

/**
 * 고객 유형 분류 기준 설정
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class ParameterMenu extends AbstractMenu {
  private static ParameterMenu parameterMenu = new ParameterMenu();
  private static final ParameterSubMenu parameterSubMenu = ParameterSubMenu.getInstance();
  private static final CustomerGroupService customerGroupService =
      CustomerGroupService.getInstance();

  private ParameterMenu() {
    super(new String[] {"Set Parameter", "View Parameter", "Update Parameter", "Back"});
  }

  public static ParameterMenu getInstance() {
    if (parameterMenu == null) {
      parameterMenu = new ParameterMenu();
    }
    return parameterMenu;
  }

  public void launch() {
    loop:
    while (true) {
      parameterMenu.show();
      try {
        switch (parameterMenu.selectMenuNumber()) {
          case 1 -> initParameter(); // 기준 초기 설정
          case 2 -> displayParameter(); // 기준 출력
          case 3 -> updateParameter(); // 기준 수정
          case 4 -> { // 이전 메뉴
            break loop;
          }
        }
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void initParameter() {
    while (true) {
      CustomerType customerType = inputCustomerType();

      if (customerGroupService.find(customerType).parameter() != null)
        System.out.println(GROUP_ALREADY_SET.getMessage());
      else customerGroupService.updateGroup(customerType, parameterSubMenu.inputParameter());

      displayCustomerGroup(customerType);
    }
  }

  private void updateParameter() {
    while (true) {
      CustomerType customerType = inputCustomerType();

      if (customerGroupService.find(customerType).parameter() == null)
        throw new AppException(NO_PARAMETER);
      else customerGroupService.updateGroup(customerType, parameterSubMenu.inputParameter());

      displayCustomerGroup(customerType);
    }
  }

  private void displayCustomerGroup(CustomerType customerType) {
    System.out.println(customerGroupService.find(customerType));
  }

  private void displayParameter() {
    while (true) {
      displayCustomerGroup(inputCustomerType());
    }
  }

  /**
   * @return 입력받은 고객 유형
   */
  private CustomerType inputCustomerType() {
    while (true) {
      System.out.println(INPUT_CUSTOMER_GROUP_MSG + "\n" + PRESS_END_MSG);
      String input = ScannerUtility.getInput().toUpperCase();

      if ("end".equalsIgnoreCase(input)) throw new AppException(INPUT_END);

      try {
        if ("".equals(input)) throw new AppException(NULL_INPUT);
        return convertInputStrToCustomerType(input);
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
