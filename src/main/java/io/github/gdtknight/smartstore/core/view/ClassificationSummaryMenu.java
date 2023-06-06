package io.github.gdtknight.smartstore.core.view;

import static io.github.gdtknight.smartstore.enums.AppMessages.INPUT_SORT_ORDER;
import static io.github.gdtknight.smartstore.enums.AppMessages.PRESS_END_MSG;
import static io.github.gdtknight.smartstore.enums.SortBy.*;
import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.INPUT_END;
import static io.github.gdtknight.smartstore.utils.StoreUtility.convertInputStrToSortOrder;

import io.github.gdtknight.smartstore.core.domain.CustomerDto;
import io.github.gdtknight.smartstore.core.domain.CustomerGroupDto;
import io.github.gdtknight.smartstore.core.service.CustomerGroupService;
import io.github.gdtknight.smartstore.core.service.CustomerService;
import io.github.gdtknight.smartstore.enums.SortBy;
import io.github.gdtknight.smartstore.enums.SortOrder;
import io.github.gdtknight.smartstore.exceptions.AppException;
import io.github.gdtknight.smartstore.utils.ScannerUtility;

/**
 * 고객 분류 정보 출력 메뉴
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class ClassificationSummaryMenu extends AbstractMenu {
  private static ClassificationSummaryMenu classificationSummaryMenu =
      new ClassificationSummaryMenu();

  private static SortBy lastRequestedSortBy = SortBy.NAME;
  private static SortOrder lastRequestedSortOrder = SortOrder.ASCENDING;

  private static final CustomerGroupService customerGroupService =
      CustomerGroupService.getInstance();
  private static final CustomerService customerService = CustomerService.getInstance();

  private ClassificationSummaryMenu() {
    super(
        new String[] {
          "Summary",
          "Summary (Sorted By Name)",
          "Summary (Sorted By Spent Time)",
          "Summary (Sorted By Total Pay Amount)",
          "Back"
        });
  }

  public static ClassificationSummaryMenu getInstance() {
    if (classificationSummaryMenu == null) {
      classificationSummaryMenu = new ClassificationSummaryMenu();
    }
    return classificationSummaryMenu;
  }

  public void launch() {
    loop:
    while (true) {
      classificationSummaryMenu.show();
      try {
        switch (classificationSummaryMenu.selectMenuNumber()) {
          case 1 -> displaySummary(); // 가장 최근에 조회한 정렬 기준에 따라. 처음엔 이름 오름차순으로
          case 2 -> displaySummarySortByName(); // 고객 그룹별 이름순 정렬
          case 3 -> displaySummarySortBySpentTime(); // 고객 그룹별 이용시간순 정렬
          case 4 -> displaySummarySortByPayAmount(); // 고객 그룹별 사용금액순 정렬
          case 5 -> {
            break loop;
          }
        }
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void displaySummarySortByName() {
    lastRequestedSortBy = NAME;
    displayResultOrderByInput();
  }

  private void displaySummarySortBySpentTime() {
    lastRequestedSortBy = SPENT_TIME;
    displayResultOrderByInput();
  }

  private void displaySummarySortByPayAmount() {
    lastRequestedSortBy = PAY_AMOUNT;
    displayResultOrderByInput();
  }

  private void displayResultOrderByInput() {
    while (true) {
      try {
        lastRequestedSortOrder = inputSortOrder();
        displaySummary();
      } catch (AppException e) {
        if (e.getErrorCode() == INPUT_END) break;
        System.out.println(e.getMessage());
      }
    }
  }

  private void displaySummary() {
    CustomerGroupDto[] customerGroupDtos = customerGroupService.findAll();
    CustomerDto[][] classifiedCustomerDtos =
        customerService.getClassifiedCustomerData(lastRequestedSortBy, lastRequestedSortOrder);

    for (int gIdx = 0; gIdx < customerGroupDtos.length; gIdx++) {
      System.out.println("\n" + customerGroupDtos[gIdx].groupTitle());

      if (classifiedCustomerDtos[gIdx] == null || classifiedCustomerDtos[gIdx].length == 0)
        System.out.println("Null.");

      for (int cIdx = 0; cIdx < classifiedCustomerDtos[gIdx].length; cIdx++) {
        System.out.println("No. " + (cIdx + 1) + " => " + classifiedCustomerDtos[gIdx][cIdx]);
      }
    }
  }

  /**
   * @return 정렬 방향 (오름차순, 내림차순)
   * @throws AppException 종료 선택시
   */
  private SortOrder inputSortOrder() throws AppException {
    while (true) {
      System.out.println(INPUT_SORT_ORDER + "\n" + PRESS_END_MSG);
      try {
        String input = ScannerUtility.getInput();
        if ("end".equalsIgnoreCase(input)) throw new AppException(INPUT_END);
        return convertInputStrToSortOrder(input);
      } catch (AppException e) {
        System.out.println(e.getMessage());
        if (e.getErrorCode() == INPUT_END) throw new AppException(INPUT_END);
      }
    }
  }
}
