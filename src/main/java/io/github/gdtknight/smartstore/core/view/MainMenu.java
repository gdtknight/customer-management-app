package io.github.gdtknight.smartstore.core.view;

import io.github.gdtknight.smartstore.exceptions.AppException;

/**
 * 메인 메뉴 화면
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class MainMenu extends AbstractMenu {
  private static MainMenu mainMenu = new MainMenu();

  private MainMenu() {
    super(new String[] {"Parameter", "Customer Data", "Classification Summary", "Quit"});
  }

  public static MainMenu getInstance() {
    if (mainMenu == null) {
      mainMenu = new MainMenu();
    }
    return mainMenu;
  }

  public void launch() {
    loop:
    while (true) {
      mainMenu.show();
      try {
        switch (mainMenu.selectMenuNumber()) {
            // 고객 유형 세분화 기준 설정
          case 1 -> ParameterMenu.getInstance().launch();

            // 고객 정보 관리
          case 2 -> CustomerMenu.getInstance().launch();

            // 고객 정보 요약
          case 3 -> ClassificationSummaryMenu.getInstance().launch();

            // 종료
          case 4 -> {
            break loop;
          }
        }
      } catch (AppException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
