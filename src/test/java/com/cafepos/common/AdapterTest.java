package com.cafepos.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.cafepos.printing.LegacyPrinterAdapter;
import com.cafepos.printing.Printer;

import vendor.legacy.LegacyThermalPrinter;

public class AdapterTest {
    @Test
    void adapter_calls_legacy_printer() {
        //Captures console output
        var originalOut = System.out;
        var output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        try {
            Printer printer = new LegacyPrinterAdapter(new LegacyThermalPrinter());
            printer.print("QQQQQ");

            String text = output.toString();
            assertTrue(text.contains("[Legacy] printing bytes: "), "Legacy printer should print");
        } finally {
            System.setOut(originalOut);
        }
    }
}
