package io.github.gdtknight.smartstore.core.view;

import static io.github.gdtknight.smartstore.enums.AppMessages.*;
import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.INPUT_END;

import io.github.gdtknight.smartstore.core.domain.Parameter;
import io.github.gdtknight.smartstore.exceptions.AppErrorCode;
import io.github.gdtknight.smartstore.exceptions.AppException;
import io.github.gdtknight.smartstore.utils.ScannerUtility;

/**
 * 고객 유형별 기준 설정 메뉴
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class ParameterSubMenu extends AbstractMenu {
  private static ParameterSubMenu parameterSubMenu = new ParameterSubMenu();

  private ParameterSubMenu() {
    super(new String[] {"Minimum Spent Time", "Minimum Pay Amount", "Back"});
  }

  /**
   * @return Singleton 객체 반환
   */
  public static ParameterSubMenu getInstance() {
    if (parameterSubMenu == null) {
      parameterSubMenu = new ParameterSubMenu();
    }
    return parameterSubMenu;
  }

  /**
   * @return 입력받은 고객 유형 분류 기준
   */
  public Parameter inputParameter() {
    Integer minimumSpentTime = null;
    Integer minimumPayAmount = null;

    while (true) {
      parameterSubMenu.show();

      try {
        switch (parameterSubMenu.selectMenuNumber()) {
          case 1 -> minimumSpentTime = inputMinimumSpentTime();
          case 2 -> minimumPayAmount = inputMinimumPayAmount();
          case 3 -> {
            return new Parameter(minimumSpentTime, minimumPayAmount);
          }
        }
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * @return 입력받은 최소 이용시간
   */
  private int inputMinimumSpentTime() {
    while (true) {
      System.out.println(INPUT_MINIMUM_SPENT_TIME_MSG + "\n" + PRESS_END_MSG);

      String input = ScannerUtility.getInput();

      if ("end".equals(input)) throw new AppException(INPUT_END);

      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println(AppErrorCode.INVALID_FORMAT.getMessage());
      }
    }
  }

  /**
   * @return 입력받은 최소 결제금액
   */
  private int inputMinimumPayAmount() {
    while (true) {
      System.out.println(INPUT_MINIMUM_PAY_AMOUNT_MSG + "\n" + PRESS_END_MSG);

      String input = ScannerUtility.getInput();

      if ("end".equals(input)) throw new AppException(INPUT_END);

      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println(AppErrorCode.INVALID_FORMAT.getMessage());
      }
    }
  }
}
