package io.github.gdtknight.smartstore.core.view;

import static io.github.gdtknight.smartstore.enums.AppMessages.INPUT_NUMBER_OF_CUSTOMERS;
import static io.github.gdtknight.smartstore.enums.AppMessages.PRESS_END_MSG;
import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.*;
import static io.github.gdtknight.smartstore.utils.ScannerUtility.getInput;
import static io.github.gdtknight.smartstore.utils.ScannerUtility.getIntegerInputSafely;

import io.github.gdtknight.smartstore.core.domain.CustomerDto;
import io.github.gdtknight.smartstore.core.service.CustomerService;
import io.github.gdtknight.smartstore.exceptions.AppException;

/**
 * 고객 정보 관리 메뉴
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class CustomerMenu extends AbstractMenu {
  private static CustomerMenu customerMenu = new CustomerMenu();
  private static final CustomerService customerService = CustomerService.getInstance();
  private static final CustomerSubMenu customerSubMenu = CustomerSubMenu.getInstance();

  /** 고객 정보 캐싱 */
  private static CustomerDto[] dtoCache = new CustomerDto[] {};

  private CustomerMenu() {
    super(
        new String[] {
          "Add Customer Data",
          "View Customer Data",
          "Update Customer Data",
          "Delete Customer Data",
          "Back"
        });
  }

  public static CustomerMenu getInstance() {
    if (customerMenu == null) {
      customerMenu = new CustomerMenu();
    }
    return customerMenu;
  }

  public void launch() {
    loop:
    while (true) {
      customerMenu.show();
      try {
        switch (customerMenu.selectMenuNumber()) {
          case 1 -> registerCustomerInfo(); // 고객 등록
          case 2 -> displayCustomerInfo(); // 고객 명단 확인
          case 3 -> updateCustomerInfo(); // 고객 정보 수정
          case 4 -> deleteCustomerInfo(); // 고객 정보 삭제
          case 5 -> {
            break loop;
          }
        }
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /** 고객 정보 등록 */
  private void registerCustomerInfo() {
    int numberOfCustomers = inputNumberOfCustomers();
    for (int idx = 0; idx < numberOfCustomers; idx++) {
      System.out.println("\n====== Customer " + (idx + 1) + ". Info. ======");
      CustomerDto newCustomer = customerSubMenu.inputCustomerInfo();
      customerService.save(newCustomer);
    }
  }

  /** 고객 정보 업데이트 */
  private void updateCustomerInfo() {
    displayCustomerInfo();
    Long id = dtoCache[inputCustomerNumber() - 1].id();
    CustomerDto updateDto = customerSubMenu.inputCustomerInfo();
    customerService.updateCustomerById(id, updateDto);
  }

  /** 고객 정보 삭제 */
  private void deleteCustomerInfo() {
    displayCustomerInfo();
    Long id = dtoCache[inputCustomerNumber() - 1].id();
    customerService.deleteCustomerById(id);
    displayCustomerInfo();
  }

  /**
   * 현재 등록된 고객 명단 출력
   *
   * @throws AppException 등록된 고객 정보가 없음
   */
  private void displayCustomerInfo() throws AppException {
    // No. 1 => Customer{userId='TEST123', name='TEST', spentTime=null, totalPay=null, group=null}
    dtoCache = customerService.findAll();
    if (dtoCache.length == 0) {
      throw new AppException(NO_CUSTOMER);
    }

    System.out.println("\n======= Customer Info. =======");
    for (int idx = 0; idx < dtoCache.length; idx++) {
      System.out.println("No. " + (idx + 1) + " => " + dtoCache[idx]);
    }
  }

  /**
   * @return 새로 등록할 고객수
   * @throws AppException 종료 선택시
   */
  private int inputNumberOfCustomers() throws AppException {
    while (true) {
      System.out.println(INPUT_NUMBER_OF_CUSTOMERS + "\n" + PRESS_END_MSG);
      String input = getInput();
      if ("end".equalsIgnoreCase(input)) throw new AppException(INPUT_END);
      try {
        int numberOfCustomers = Integer.parseInt(input);
        if (numberOfCustomers == -1) {
          throw new AppException(INVALID_FORMAT);
        }
        return numberOfCustomers;
      } catch (NumberFormatException e) {
        System.out.println(INVALID_FORMAT.getMessage());
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * @return 출력된 명단 중 수정하고자 하는 고객의 번호
   */
  private int inputCustomerNumber() {
    while (true) {
      System.out.println("Which customer ( 1 ~ " + customerService.getNumberOfCustomers() + " )? ");
      try {
        int customerNumber = getIntegerInputSafely();
        if (customerNumber < 1 || customerNumber > customerService.getNumberOfCustomers()) {
          throw new AppException(INVALID_FORMAT);
        }
        return customerNumber;
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
