package ESC.Week3;

import java.util.ArrayList;

public class VisitorPatternOriginal {

    public static void main(String[] args) {
        SUTD oneSUTD = new SUTD();
        Auditor auditor = new Auditor();

        oneSUTD.accept(auditor);
    }
}

class SUTD implements Auditable {
    private ArrayList<Employee> employee;

    public SUTD() {
        employee = new ArrayList<Employee>();
        employee.add(new Professor("Sun Jun", 0));
        employee.add(new AdminStaff("Stacey", 5));
        employee.add(new Student("Allan", 3));
    }

    public ArrayList<Employee> getEmployee() {
        return employee;
    }

    public void accept(AuditVisitor auditor) {
        for (Employee employeeName :
                employee) {
            employeeName.accept(auditor);
        }
        auditor.visit(this);
    }
}

class Employee implements Auditable {
    @Override
    public void accept(AuditVisitor auditor) {
    }
}

interface Auditable {
    void accept(AuditVisitor auditor);
}

class Professor extends Employee implements Auditable {
    private String name;
    private int no_of_publications;

    public Professor(String name, int no_of_publications) {
        this.name = name;
        this.no_of_publications = no_of_publications;
    }

    public String getName() {
        return name;
    }

    public int getNo_of_publications() {
        return no_of_publications;
    }

    @Override
    public void accept(AuditVisitor auditor) {
        auditor.visit(this);
    }
}

class AdminStaff extends Employee implements Auditable {
    private String name;
    private float efficiency;

    public AdminStaff(String name, float efficiency) {
        this.name = name;
        this.efficiency = efficiency;
    }

    public String getName() {
        return name;
    }

    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public void accept(AuditVisitor auditor) {
        auditor.visit(this);
    }
}

class Student extends Employee implements Auditable {
    private String name;
    private float GPA;

    public Student(String name, float GPA) {
        this.name = name;
        this.GPA = GPA;
    }

    public String getName() {
        return name;
    }

    public float getGPA() {
        return GPA;
    }

    @Override
    public void accept(AuditVisitor auditor) {
        auditor.visit(this);
    }
}

interface AuditVisitor {
    void visit(SUTD sutd);

    void visit(Professor professor);

    void visit(AdminStaff adminStaff);

    void visit(Student student);
}

class Auditor implements AuditVisitor {
    @Override
    public void visit(SUTD sutd) {
        System.out.println("Visiting SUTD.");
    }

    @Override
    public void visit(Professor professor) {
        System.out.println("Prof: " + professor.getName() + " " + professor.getNo_of_publications());
    }

    @Override
    public void visit(AdminStaff adminStaff) {
        System.out.println("Admin Staff: " + adminStaff.getName() + " efficiency: " + adminStaff.getEfficiency());
    }

    @Override
    public void visit(Student student) {
        System.out.println("Student: " + student.getName() + " GPA: " + student.getGPA());
    }
}