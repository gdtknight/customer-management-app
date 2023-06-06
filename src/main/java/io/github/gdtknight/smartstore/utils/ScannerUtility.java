package io.github.gdtknight.smartstore.utils;

import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.EMPTY_INPUT;
import static io.github.gdtknight.smartstore.exceptions.AppErrorCode.INTERNAL_ERROR;

import io.github.gdtknight.smartstore.exceptions.AppException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 사용자로부터 입력을 받기 위한 유틸리티
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class ScannerUtility {
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * @return 정수로 변환된 결과. 공백일 경우 -1.
   */
  public static int getIntegerInputSafely() {
    String converted = scanner.nextLine().replaceAll("[^0-9]", "");
    return "".equals(converted) ? -1 : Integer.parseInt(converted);
  }

  public static String getInput() throws AppException {
    try {
      return scanner.nextLine();
    } catch (NoSuchElementException e) {
      throw new AppException(EMPTY_INPUT);
    } catch (IllegalStateException e) {
      throw new AppException(INTERNAL_ERROR);
    }
  }
}
