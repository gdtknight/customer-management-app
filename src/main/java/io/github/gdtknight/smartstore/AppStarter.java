package io.github.gdtknight.smartstore;

import io.github.gdtknight.smartstore.core.view.MainMenu;

/**
 * 스마트스토어 메인 시작점
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class AppStarter {
  private AppStarter() {}

  private static AppStarter appStarter = new AppStarter();

  public static AppStarter getInstance() {
    if (appStarter == null) {
      appStarter = new AppStarter();
    }

    return appStarter;
  }

  public void run() {
    title();
    MainMenu.getInstance().launch();
    finish();
  }

  private void title() {
    System.out.print(
        """
        ===========================================
        Title : SmartStore Customer Segmentation
        Release Date : 23.04.24
        Copyright 2023 YongHo All rights reserved.
        ===========================================
        """);
  }

  private void finish() {
    System.out.println("Program Finished.");
  }
}
