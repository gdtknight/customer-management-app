package io.github.gdtknight.smartstore.enums;

/**
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public enum SortOrder {
  A("Ascending"),
  D("Descending"),
  ASCENDING("Ascending"),
  DESCENDING("Descending");

  private final String sortOrder;

  SortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public SortOrder replaceAbbreviation() {
    if (this == A) return ASCENDING;
    else if (this == D) return DESCENDING;
    else return this;
  }
}
