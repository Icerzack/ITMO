package com.demo;

import com.demo.function.Function;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CsvWriter {

  public static void write(
      final String filename,
      final Function function,
      final BigDecimal from,
      final BigDecimal to,
      final BigDecimal step,
      final BigDecimal precision)
      throws IOException {
    final Path path = Paths.get(filename);
    final File file = new File(path.toUri());
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    final PrintWriter printWriter = new PrintWriter(file);
    for (BigDecimal current = from; current.compareTo(to) <= 0; current = current.add(step)) {
      printWriter.println(current + "," + function.calculate(current, precision));
    }
    printWriter.close();
  }

}
