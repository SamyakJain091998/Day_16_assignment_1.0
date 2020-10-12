package com.IOStream.practice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;
import static com.IOStream.practice.EmployeePayrollService.IOService.FILE_IO;
import static com.IOStream.practice.EmployeePayrollService.IOService.CONSOLE_IO;
import static com.IOStream.practice.EmployeePayrollService.IOService.DB_IO;

import static org.junit.Assert.*;
import org.junit.Ignore;

public class EmployeePayrollServiceTest {

	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchNumberOfEmployeeEntries() throws IOException {
		EmployeePayrollData[] arrayOfEmps = { new EmployeePayrollData(1, "samyak", 100000),
				new EmployeePayrollData(2, "manu", 200000), new EmployeePayrollData(3, "sam", 300000) };

		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(FILE_IO);
		EmployeePayrollFileIOService employeePayrollFileIOService = new EmployeePayrollFileIOService();
		long entries = employeePayrollFileIOService.countEntries();
		Assert.assertEquals(entries, 3);
	}
}
