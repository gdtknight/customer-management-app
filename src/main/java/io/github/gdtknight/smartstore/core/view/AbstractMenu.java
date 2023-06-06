package io.github.gdtknight.smartstore.core.view;

import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.INVALID_FORMAT;
import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.INVALID_INPUT;

import io.github.gdtknight.smartstore.exceptions.AppException;
import io.github.gdtknight.smartstore.utils.ScannerUtility;

/**
 * 메뉴 출력 및 선택
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public abstract class AbstractMenu {
  /** 선택 가능 메뉴 항목 */
  private final String[] items;

  public AbstractMenu(String[] items) {
    this.items = items;
  }

  /** 메뉴 출력 */
  void show() {
    StringBuilder menuBuilder = new StringBuilder().append("\n==============================\n");
    for (int i = 0; i < items.length; i++) {
      menuBuilder.append(i + 1).append(". ").append(items[i]).append("\n");
    }
    menuBuilder.append("==============================");
    System.out.println(menuBuilder);
  }

  /**
   * 메뉴 입력
   *
   * @return 입력된 메뉴 번호
   */
  int selectMenuNumber() throws AppException {
    System.out.print("Choose One: ");

    int selection = ScannerUtility.getIntegerInputSafely();

    if (selection == -1) {
      throw new AppException(INVALID_FORMAT);
    }

    if (selection < 1 || selection > items.length) {
      throw new AppException(INVALID_INPUT);
    }

    return selection;
  }
}
