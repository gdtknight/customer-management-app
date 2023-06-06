package io.github.gdtknight.smartstore.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 고객 유형
 *
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public enum CustomerType {
  NONE("N", "해당없음"),
  GENERAL("G", "일반고객"),
  VIP("V", "우수고객"),
  VVIP("VV", "최우수고객");

  private static final Map<String, String> ABBREVIATION_MAP;

  static {
    Map<String, String> map =
        Stream.of(values())
            .collect(Collectors.toMap(CustomerType::getAbbreviation, CustomerType::name));
    Stream.of(values()).forEach(type -> map.put(type.name(), type.name()));
    ABBREVIATION_MAP = Collections.unmodifiableMap(map);
  }

  private final String abbreviation;
  private final String description;

  CustomerType(String abbreviation, String description) {
    this.abbreviation = abbreviation;
    this.description = description;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public String getDescription() {
    return description;
  }

  public static CustomerType of(final String valueStr) {
    return CustomerType.valueOf(ABBREVIATION_MAP.get(valueStr));
  }
}
