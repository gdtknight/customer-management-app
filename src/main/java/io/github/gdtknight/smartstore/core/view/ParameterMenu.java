package io.github.gdtknight.smartstore.core.view;

import static io.github.gdtknight.smartstore.enums.AppMessages.INPUT_CUSTOMER_GROUP_MSG;
import static io.github.gdtknight.smartstore.enums.AppMessages.PRESS_END_MSG;
import static io.github.gdtknight.smartstore.exceptions.StoreErrorCode.*;
import static io.github.gdtknight.smartstore.utils.StoreUtility.convertInputStrToCustomerType;

import io.github.gdtknight.smartstore.core.domain.CustomerGroupDto;
import io.github.gdtknight.smartstore.core.service.CustomerGroupService;
import io.github.gdtknight.smartstore.enums.CustomerType;
import io.github.gdtknight.smartstore.exceptions.StoreException;
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
          case 2 -> showParameter(); // 기준 출력
          case 3 -> updateParameter(); // 기준 수정
          case 4 -> { // 이전 메뉴
            break loop;
          }
        }
      } catch (StoreException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void initParameter() {
    while (true) {
      CustomerGroupDto customerGroupDto = customerGroupService.find(inputCustomerType());

      if (customerGroupDto.parameter() != null) {
        System.out.println(GROUP_ALREADY_SET.getMessage());
      } else {
        customerGroupDto =
            customerGroupService.updateGroupParameter(
                customerGroupDto, parameterSubMenu.inputParameter());
      }

      System.out.println(customerGroupDto);
    }
  }

  private void updateParameter() {
    while (true) {
      CustomerGroupDto customerGroupDto = customerGroupService.find(inputCustomerType());

      if (customerGroupDto.parameter() == null) throw new StoreException(NO_PARAMETER);

      customerGroupDto =
          customerGroupService.updateGroupParameter(
              customerGroupDto, parameterSubMenu.inputParameter());

      System.out.println(customerGroupDto);
    }
  }

  private void showParameter() {
    while (true) {
      System.out.println(customerGroupService.find(inputCustomerType()));
    }
  }

  /**
   * @return 입력받은 고객 유형
   */
  private CustomerType inputCustomerType() {
    while (true) {
      System.out.println(INPUT_CUSTOMER_GROUP_MSG + "\n" + PRESS_END_MSG);
      String input = ScannerUtility.getInput().toUpperCase();

      if ("end".equalsIgnoreCase(input)) throw new StoreException(INPUT_END);

      try {
        if ("".equals(input)) throw new StoreException(NULL_INPUT);
        return convertInputStrToCustomerType(input);
      } catch (StoreException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
