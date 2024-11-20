//Homework 3
//Adam Stahly
//Brief Description of Program
// Reads employee data from a file and displays payroll information.
// Displays relevant data for each employee type.
import java.util.*;
import java.io.*;

abstract class Employee {
    private int _id;
    private String _name;

    public Employee() {
        _id = 0;
        _name = "";
    }
    public Employee(int id, String name) {
        _id = id;
        _name = name;
    }
    public void setId(int i) {_id = i;}
    public void setName(String n) {_name = n;}
    public int getId() {return _id;}
    public String getName() {return _name;}
    public abstract double calculatePay();
    public String toString() {
        return _id + " " + _name;
    }
    public boolean equals(Employee e){
        return e.getId() == _id
                && e.getName().equals(_name);
    }
}
class FullTimeEmp extends Employee {
    private int _salary;

    public FullTimeEmp() {
        _salary = 0;
    }
    public FullTimeEmp(int id, String name,int salary){
        super(id,name);
        _salary = salary;
    }

    public void setSalary(int s) { _salary = s;}
    public int getSalary() { return _salary;}
    public double calculatePay() {
        return _salary / 24.0;
    }
    public String toString() {
        return super.toString() + " 1 " + _salary;
    }
    public boolean equals(Employee e) {
        if (e instanceof FullTimeEmp f){
            return super.equals(f)
                && f.getSalary() == _salary;
        }
        return false;
    }
}
class PartTimeEmp extends Employee {
    private double _hoursWorked;
    private double _hourlyPayRate;

    public PartTimeEmp() {
        _hoursWorked = 0.0;
        _hourlyPayRate = 0.0;
    }
    public PartTimeEmp(int id, String name,
                       double hoursWorked, double hourlyPayRate) {
        super(id,name);
        _hoursWorked = hoursWorked;
        _hourlyPayRate = hourlyPayRate;
    }

    public void setHoursWorked(double hours) { _hoursWorked = hours;}
    public void setHourlyPayRate(double rate) { _hourlyPayRate = rate;}
    public double getHoursWorked() {return _hoursWorked;}
    public double getHourlyPayRate() {return _hourlyPayRate;}
    public double calculatePay() {
        return _hoursWorked * _hourlyPayRate;
    }
    public String toString() {
        return super.toString() + " 2 " +
                _hoursWorked + " " + _hourlyPayRate;
    }
    public boolean equals(Employee e) {
        if (e instanceof PartTimeEmp p) {
            return super.equals(p)
                    && p.getHoursWorked() == _hoursWorked
                    && p.getHourlyPayRate() == _hourlyPayRate;
        }
        return false;
    }
}
class Contractor extends Employee {
    private int _projectsCompleted;
    private double _ratePerProject;

    public Contractor() {
        _projectsCompleted = 0;
        _ratePerProject = 0.0;
    }
    public Contractor(int id, String name,
                      int projectsCompleted, double ratePerProject) {
        super(id,name);
        _projectsCompleted = projectsCompleted;
        _ratePerProject = ratePerProject;
    }

    public void setProjectsCompleted(int p) { _projectsCompleted = p;}
    public void setRatePerProject(double r) { _ratePerProject = r;}
    public int getProjectsCompleted() {return _projectsCompleted;}
    public double getRatePerProject() {return _ratePerProject;}
    public double calculatePay() {
        return ((double) _projectsCompleted) * _ratePerProject;
    }
    public String toString() {
        return super.toString() + " 3 " +
                _projectsCompleted +
                " " + _ratePerProject;
    }
    public boolean equals(Employee e) {
        if (e instanceof Contractor c) {
            return super.equals(c)
                    && c.getProjectsCompleted() == _projectsCompleted
                    && c.getRatePerProject() == _ratePerProject;
        }
        return false;
    }
}

public class Main {
    public static void populate(ArrayList<Employee> employees) {
        try {
            Scanner infile = new Scanner(new File("Company.txt"));
            int employeeType , id, salary, projectsCompleted;
            double hoursWorked, hourlyPayRate, ratePerProject;
            String name;
            while (infile.hasNext()) {
                String[] employeeInfo = infile.nextLine().split(",");
                employeeType = Integer.parseInt(employeeInfo[0]);
                id = Integer.parseInt(employeeInfo[1]);
                name = employeeInfo[2];
                if (employeeType == 1) {
                    salary = Integer.parseInt(employeeInfo[3]);
                    employees.add(new FullTimeEmp(id,name,salary));
                }
                else if (employeeType == 2) {
                    hoursWorked = Double.parseDouble(employeeInfo[3]);
                    hourlyPayRate = Double.parseDouble(employeeInfo[4]);
                    employees.add(new PartTimeEmp(id,name,
                            hoursWorked,hourlyPayRate));
                }
                else {
                    projectsCompleted = Integer.parseInt
                            (employeeInfo[3]);
                    ratePerProject = Double.parseDouble(employeeInfo[4]);
                    employees.add(new Contractor(id,name,
                            projectsCompleted,ratePerProject));
                }
            }
        }
        catch (Exception e) {
            System.out.println("Problem With File");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        populate(employees);
        System.out.println("Press 1 for Full Time Payroll Information:");
        System.out.println("Press 2 for Part Time Payroll Information:");
        System.out.println("Press 3 for Contractor Payroll Information:");
        System.out.println("Press 4 to Stop the Program:");
        int userInput = scan.nextInt();
        while (userInput != 4) {
            switch (userInput) {
                case 1:
                    System.out.printf("%-9s %-15s %-11s %-6s%n","ID",
                            "Name","Salary","Bi-Weekly Pay");
                    for (Employee employee : employees) {
                        if (employee instanceof FullTimeEmp) {
                            System.out.printf("%-9d %-16s",
                                    employee.getId(),
                                    employee.getName());
                            System.out.printf("$%-11d",
                                    ((FullTimeEmp)
                                            employee).getSalary());
                            System.out.printf("$%.2f%n",
                                    employee.calculatePay());
                        }
                    }
                    break;
                case 2:
                    System.out.printf("%-9s %-15s %-14s %-11s %s%n","ID",
                            "Name", "Hours Worked","Pay Rate","Weekly Pay");
                    for (Employee employee : employees) {
                        if (employee instanceof PartTimeEmp) {
                            System.out.printf("%-9d %-16s",
                                    employee.getId(),
                                    employee.getName());
                            System.out.printf("%-14.1f $%-11.2f",
                                    ((PartTimeEmp)
                                            employee).getHoursWorked(),
                                    ((PartTimeEmp)
                                            employee).getHourlyPayRate());
                            System.out.printf("$%.2f%n",
                                    employee.calculatePay());
                        }
                    }
                    break;
                case 3:
                    System.out.printf("%-9s %-15s %-20s %-22s %s%n","ID",
                            "Name","Projects Completed",
                            "Pay Rate Per Project","Current Pay Due");
                    for (Employee employee : employees) {
                        if (employee instanceof Contractor) {
                            System.out.printf("%-9d %-16s",
                                    employee.getId(),
                                    employee.getName());
                            System.out.printf("%-20d $%-22.2f",
                                    ((Contractor) employee).
                                            getProjectsCompleted(),
                                    ((Contractor) employee).
                                            getRatePerProject());
                            System.out.printf("$%.2f%n",
                                    employee.calculatePay());
                        }
                    }
                    break;
                default:
                    System.out.println("Not a Valid Input");
            }
            System.out.println();
            System.out.println("Press 1 for Full Time Payroll Information:");
            System.out.println("Press 2 for Part Time Payroll Information:");
            System.out.println("Press 3 for Contractor " +
                    "Payroll Information:");
            System.out.println("Press 4 to Stop the Program:");
            userInput = scan.nextInt();
        }
        System.out.println("Thank You For Using This Program!");
    }
}