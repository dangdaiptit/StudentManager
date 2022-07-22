import java.util.ArrayList;
import java.util.Scanner;

public class Validation {

    private final static Scanner sc = new Scanner(System.in);

    //check user input number limit
    public static int checkInputLimit(int min, int max) {
        while (true) {
            try {
                int result = Integer.parseInt(sc.nextLine().trim());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input number in rage [" + min + "," + max + "]");
                System.out.print("Enter again: ");
            }
        }
    }

    //check user input string
    public static String checkInputString() {
        while (true) {
            String result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.err.println("Not empty");
                System.out.println("Enter again: ");
            } else {
                return result;
            }
        }
    }

    //check user input yes/no
    public static boolean checkInputYN() {
        while (true) {
            String result = checkInputString();
            //return true if user input y/Y
            if (result.equalsIgnoreCase("Y")) {
                return true;
            }
            //return false if user input n/N
            if (result.equalsIgnoreCase("N")) {
                return false;
            }

            System.err.println("Please input y/Y or n/N");
            System.out.print("Enter again: ");


        }
    }

    //check user input u/D
    public static boolean checkInputUD() {
        while (true) {
            String result = checkInputString();
            //return true if user input u/U
            if (result.equalsIgnoreCase("U")) {
                return true;
            }

            //return false if user input d/D
            if (result.equalsIgnoreCase("D")) {
                return false;
            }

            System.err.println("Please input u/D or d/D!");
            System.out.print("Enter again: ");
        }
    }

    public static boolean checkInputUE(){
        while (true){
            String result = checkInputString();

            if (result.equalsIgnoreCase("U")){
                return true;
            }
            if (result.equalsIgnoreCase("E")){
                return false;
            }

            System.err.println("Please input u/U or e/E!");
            System.out.print("Enter again: ");
        }
    }


    //check user input course
    public static String checkInputCourse() {
        while (true) {
            String result = checkInputString();
            //check input course in java/ .net/ c/c++
            if (result.equalsIgnoreCase("java")
                    || result.equalsIgnoreCase(".net")
                    || result.equalsIgnoreCase("c/c++")) {
                return result;
            }

            System.err.println("There are only three course: Java, .Net, C/C++");
            System.out.print("Enter again: ");
        }
    }

    //check id student exist? return false if existed, return true if not exist
    public static boolean checkId(ArrayList<Student> ls, String id) {
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())) {
                return false;
            }
        }
        return true;
    }


    //check id and exist
    public static boolean checkIdExist(ArrayList<Student> ls, String id, String studentName) {
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())
                    && studentName.equalsIgnoreCase(student.getStudentName())) {
                return false;
            }
        }
        return true;
    }

    //check student exist, if exist return false, if not exist return true
    public static boolean checkStudentExist(ArrayList<Student> ls, String id, String studentName,
                                            String semester, String courseName) {
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())
                    && studentName.equalsIgnoreCase(student.getStudentName())
                    && semester.equalsIgnoreCase(student.getSemester())
                    && courseName.equalsIgnoreCase(student.getCourseName())) {
                return false;
            }
        }
        return true;
    }

    //get nameStudent by Id
    public static String getNameStudentById(ArrayList<Student> ls, String id) {
        String nameById = null;
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())) {
                nameById = student.getStudentName();
            }
        }
        return nameById;
    }

    //check user information when update
    public static boolean checkChangeInformation(Student student, String id, String studentName, String semester,
                                                 String courseName) {

        if (id.equalsIgnoreCase(student.getId())
                && studentName.equalsIgnoreCase(student.getStudentName())
                && semester.equalsIgnoreCase(student.getSemester())
                && courseName.equalsIgnoreCase(student.getCourseName())) {
            return false;
        }
        return true;
    }

    public static boolean checkReportExist(ArrayList<Report> lr, String name, String course, int total) {
        for (Report report : lr) {
            if (name.equalsIgnoreCase(report.getStudentName())
                    && course.equalsIgnoreCase(report.getCourseName())
                    && total == report.getTotalCourse()) {
                return false;
            }
        }
        return true;
    }


}
