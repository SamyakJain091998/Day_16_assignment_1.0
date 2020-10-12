package com.IOStream.practice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class EmployeePayrollFileIOService {
	public static String PAYROLL_FILE_NAME = "payroll-file.txt";

	public void writeData(List<EmployeePayrollData> employeePayrollList) {
		StringBuffer empBuffer = new StringBuffer();
		employeePayrollList.forEach(employee -> {
			String employeeDataString = employee.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(PAYROLL_FILE_NAME), empBuffer.toString().getBytes());
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	public List<EmployeePayrollData> processFile() throws IOException {
		List<EmployeePayrollData> processedList = new ArrayList<>();
		System.out.println("Inside processFile() function");

		Stream<String> input = Files.lines(new File("payroll-file.txt").toPath()).map(line -> line.trim());
		List<String> myList = input.collect(Collectors.toList());
		System.out.println(myList);
		System.out.println();
		for (String str : myList) {
			str = str.replaceAll(" ", "");
			str = str.replaceAll(":", ",");
			String[] strArray = str.split(",");

			String nameOfEmployee = null;
			int id = 0;
			long salary = 0;

			for (String strDemo : strArray) {
				String regex = "^[a-z]{1,}$";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(strDemo);
				if (m.matches()) {
					nameOfEmployee = strDemo;
				}
				String regex1 = "^[1-9]{1}[0-9]{0,2}$";
				Pattern p1 = Pattern.compile(regex1);
				Matcher m1 = p1.matcher(strDemo);
				if (m1.matches()) {
					id = Integer.valueOf(strDemo);
				}
				String regex2 = "^[1-9]{1}[0-9]{3,}$";
				Pattern p2 = Pattern.compile(regex2);
				Matcher m2 = p2.matcher(strDemo);
				if (m2.matches()) {
					salary = Long.valueOf(strDemo);
				}
			}
			EmployeePayrollData localObj = new EmployeePayrollData(id, nameOfEmployee, salary);
			processedList.add(localObj);
		}

		return processedList;
	}

	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File("payroll-file.txt").toPath()).count();
		} catch (IOException e) {
			// TODO: handle exception
		}
		return entries;
	}
}