package com.IOStream.practice;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO
	}

	private List<EmployeePayrollData> employeePayrollList;

	public EmployeePayrollService() {

	}

	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		// TODO Auto-generated constructor stub
		this.employeePayrollList = employeePayrollList;
	}

	public static void main(String[] args) {
		ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner sc = new Scanner(System.in);
		employeePayrollService.readEmployeePayrollData(sc);
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
	}

	private void readEmployeePayrollData(Scanner sc) {
		System.out.println("Enter employee id : ");
		int id = sc.nextInt();
		System.out.println("Enter employee name : ");
		String name = sc.nextLine();
		System.out.println("Enter employee salary : ");
		long salary = sc.nextLong();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}

	public void writeEmployeePayrollData(IOService ioService) {
		// TODO Auto-generated method stub
		if (ioService.equals(IOService.CONSOLE_IO)) {
			System.out.println("Writing on console : " + employeePayrollList);
		} else if (ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
		}
	}
}