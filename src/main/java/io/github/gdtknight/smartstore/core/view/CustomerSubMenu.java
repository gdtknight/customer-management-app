package io.github.gdtknight.smartstore.core.view;

import static io.github.gdtknight.smartstore.enums.AppMessages.*;
import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.*;

import io.github.gdtknight.smartstore.core.domain.CustomerDto;
import io.github.gdtknight.smartstore.exceptions.AppErrorCode;
import io.github.gdtknight.smartstore.exceptions.AppException;
import io.github.gdtknight.smartstore.utils.ScannerUtility;

/**
 * 고객 세부 정보 관리 메뉴
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class CustomerSubMenu extends AbstractMenu {
  private static final CustomerSubMenu customerSubMenu = new CustomerSubMenu();

  private CustomerSubMenu() {
    super(
        new String[] {
          "Customer Name", "Customer ID", "Customer Spent Time", "Customer Total Pay Amount", "Back"
        });
  }

  public static CustomerSubMenu getInstance() {
    return customerSubMenu;
  }

  /**
   * @return 입력 받은 고객 세부 정보
   */
  public CustomerDto inputCustomerInfo() {
    String customerName = null;
    String customerID = null;
    Integer customerSpentTime = null;
    Integer customerPayAmount = null;

    loop:
    while (true) {
      customerSubMenu.show();

      try {
        switch (customerSubMenu.selectMenuNumber()) {
          case 1 -> customerName = customerSubMenu.inputCustomerName();
          case 2 -> customerID = customerSubMenu.inputCustomerId();
          case 3 -> customerSpentTime = customerSubMenu.inputCustomerSpentTime();
          case 4 -> customerPayAmount = customerSubMenu.inputCustomerPayAmount();
          case 5 -> {
            break loop;
          }
        }
      } catch (AppException e) {
        System.out.println(e.getMessage());
        if (e.getErrorCode() == INPUT_END) break;
      }
    }

    return CustomerDto.of(customerName, customerID, customerSpentTime, customerPayAmount);
  }

  /**
   * @return 입력 받은 고객 이름
   * @throws AppException 종료 선택시
   */
  private String inputCustomerName() throws AppException {
    System.out.println(INPUT_CUSTOMER_NAME + "\n" + PRESS_END_MSG);
    String input = ScannerUtility.getInput();
    if ("end".equalsIgnoreCase(input)) throw new AppException(INPUT_END);
    return input;
  }

  /**
   * @return 입력 받은 고객 ID
   * @throws AppException 종료 선택시
   */
  private String inputCustomerId() throws AppException {
    System.out.println(INPUT_CUSTOMER_ID + "\n" + PRESS_END_MSG);
    String input = ScannerUtility.getInput();
    if ("end".equalsIgnoreCase(input)) throw new AppException(INPUT_END);
    return input;
  }

  /**
   * @return 입력 받은 고객의 이용 시간
   * @throws AppException 종료 선택시
   */
  private int inputCustomerSpentTime() throws AppException {
    while (true) {
      System.out.println(INPUT_CUSTOMER_SPENT_TIME + "\n" + PRESS_END_MSG);
      String input = ScannerUtility.getInput();
      if ("end".equalsIgnoreCase(input)) throw new AppException(INPUT_END);
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println(AppErrorCode.INVALID_FORMAT.getMessage());
      }
    }
  }

  /**
   * @return 입력 받은 고객의 결제 금액
   * @throws AppException 종료 선택시
   */
  private int inputCustomerPayAmount() throws AppException {
    while (true) {
      System.out.println(INPUT_CUSTOMER_PAY_AMOUNT + "\n" + PRESS_END_MSG);
      String input = ScannerUtility.getInput();
      if ("end".equalsIgnoreCase(input)) throw new AppException(INPUT_END);
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println(AppErrorCode.INVALID_FORMAT.getMessage());
      }
    }
  }
}
