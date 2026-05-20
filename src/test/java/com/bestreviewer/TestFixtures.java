package com.bestreviewer;

import java.nio.file.Path;
import java.nio.file.Paths;

/** src/test/resources/fixtures 경로 헬퍼. */
final class TestFixtures {

  private TestFixtures() {}

  static String path(String fileName) {
    Path base = Paths.get("src", "test", "resources", "fixtures");
    return base.resolve(fileName).toString();
  }
}
