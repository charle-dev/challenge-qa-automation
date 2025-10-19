package com.accenture.challenge;

import java.util.ArrayList;
import java.util.List;

public class CucumberCli {
  public static void main(String[] args) {
    String tags     = System.getProperty("cucumber.filter.tags");
    String features = System.getProperty("cucumber.features", "classpath:features");
    String glue     = System.getProperty("cucumber.glue", "com.accenture.challenge");
    String plugin   = System.getProperty("cucumber.plugin", "pretty,html:target/cucumber-report.html");

    List<String> cli = new ArrayList<>();
    cli.add("--glue");   cli.add(glue);

    for (String p : plugin.split("\\s*,\\s*")) {
      if (!p.isBlank()) { cli.add("--plugin"); cli.add(p); }
    }

    if (tags != null && !tags.isBlank()) {
      cli.add("--tags"); cli.add(tags);
    }

    cli.add(features);

    io.cucumber.core.cli.Main.main(cli.toArray(new String[0]));
  }
}
