import java.util.ArrayList;
import java.util.Collections;

public class Manager {


    //show menu
    public static void menu() {
        System.out.println("1. Create");
        System.out.println("2. Find and Sort");
        System.out.println("3. Update/Delete");
        System.out.println("4. Report");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    //Create new student
    public static void createStudent(int count, ArrayList<Student> ls) {
        if (count > 10) {
            System.out.print("Do you want to continue (Y/N): ");
            if (!Validation.checkInputYN()) {
                return;
            }
        }

        while (true) {
            System.out.print("Enter id: ");
            String id = Validation.checkInputString();
            boolean checkIdExist = false;
            String name = null;
            if (!Validation.checkId(ls, id)) {
                System.err.println("Id already exists. Please re-input.");
                System.out.println("Do you want to continue (Y/N)?");
                if (Validation.checkInputYN()) {
                    name = Validation.getNameStudentById(ls, id);
                    System.out.println("NameStudent by Id: " + id + " is: " + name);
                    checkIdExist = true;
                } else continue;
            }
            if (!checkIdExist) {
                System.out.print("Enter name student: ");
                name = Validation.checkInputString();
//                if (!Validation.checkIdExist(ls, id, name)) {
//                    System.err.print("Id has exist student. Please re-input.");
//                    continue;
//                }
            }

            System.out.print("Enter semester: ");
            String semester = Validation.checkInputString();
            System.out.print("Enter name course: ");
            String course = Validation.checkInputCourse();
            //check student exist or not
            if (Validation.checkStudentExist(ls, id, name, semester, course)) {
                ls.add(new Student(id, name, semester, course));
                count++;
                System.out.println("\tAdd student success.");
                return;
            }
            System.err.println("This student information already exists! Please re-input.");
        }
    }

    //Find and Sort
    public static void findAndSort(ArrayList<Student> ls) {
        //check ls hollow? return true if ls hollow and opposite
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        ArrayList<Student> listStudentFindByName = listStudentFindByName(ls);
        if (listStudentFindByName.isEmpty()) {
            System.err.println("Not exist.");
        } else {
            Collections.sort(listStudentFindByName);
            System.out.printf("%-10s%-15s%-15s%-15s\n", "ID", "StudentName", "Semester", "CourseName");
            for (Student student : listStudentFindByName) {
                student.printStudent();
            }
        }
    }

    //list student found by name
    private static ArrayList<Student> listStudentFindByName(ArrayList<Student> ls) {
        ArrayList<Student> listStudentFindByName = new ArrayList<>();
        System.out.print("Enter name to search: ");
        String name = Validation.checkInputString();
        for (Student student : ls) {
            //check student have in name by contains? return true if yes, return false if not
            if (student.getStudentName().contains(name)) {
                listStudentFindByName.add(student);
            }
        }
        return listStudentFindByName;
    }

    //Update and Delete
    public static void updateOrDelete(ArrayList<Student> ls, int count) {
        if (ls.isEmpty()) {
            System.err.println("List Empty.");
            return;
        }
        System.out.println("Enter ID to search: ");
        String id = Validation.checkInputString();
        ArrayList<Student> listStudentUpdate = getListStudentById(ls, id);

        if (listStudentUpdate.isEmpty()) {
            System.err.println("Not found student.");
            return;
        } else {
            Student student = getStudentByListFound(listStudentUpdate);
            System.out.print("Do you want Update (U) or Delete (D) student: ");
            //check user want to update or delete
            if (Validation.checkInputUD()) {
                System.out.print("Enter id: ");
                String idStudent = Validation.checkInputString();
                System.out.print("Enter name: ");
                String name = Validation.checkInputString();
                System.out.print("Enter semester: ");
                String semester = Validation.checkInputString();
                System.out.print("Enter course: ");
                String course = Validation.checkInputCourse();

                //check user change or not
                if (!Validation.checkChangeInformation(student, idStudent, name, semester, course)) {
                    System.err.println("Nothing change.");
                }

                //check student exist or not
                if (Validation.checkStudentExist(ls, idStudent, name, semester, course)) {
                    student.setId(idStudent);
                    student.setStudentName(name);
                    student.setSemester(semester);
                    student.setCourseName(course);
                    System.err.println("Update success.");
                }
                return;
            } else {
                ls.remove(student);
                count--;
                System.err.println("Delete success.");
                return;
            }
        }
    }

    //Get list student want to update/delete found by id
    public static Student getStudentByListFound(ArrayList<Student> listStudentByListFound) {
        System.out.println("==== List student found ====");
        int count = 1;
        System.out.printf("%-10s%-10s%-15s%-15s%-15s\n", "STT", "ID", "Name", "Semester", "CourseName");
        //display list student found
        for (Student student : listStudentByListFound) {
            System.out.printf("%-10s%-10s%-15s%-15s%-15s\n", count, student.getId(), student.getStudentName(),
                    student.getSemester(), student.getCourseName());
            count++;
        }
        System.out.print("Enter student: ");
        int choice = Validation.checkInputLimit(1, listStudentByListFound.size());
        return listStudentByListFound.get(choice - 1);
    }

    //get list find student by id
    public static ArrayList<Student> getListStudentById(ArrayList<Student> ls, String id) {
        ArrayList<Student> getListStudentById = new ArrayList<>();
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())) {
                getListStudentById.add(student);
            }
        }
        return getListStudentById;
    }

    //print Report
    public static void report(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List Empty.");
            return;
        }
        ArrayList<Report> lr = new ArrayList<>();
        for (int i = 0; i < ls.size(); i++) {
            int total = 0;
            for (Student student :
                    ls) {
                String id = student.getId();
                String studentName = student.getStudentName();
                String courseName = student.getCourseName();
                for (Student studentCountTotal :
                        ls) {
                    if (id.equalsIgnoreCase(studentCountTotal.getId())
                            && studentName.equalsIgnoreCase(studentCountTotal.getStudentName())) {
                        total++;
                    }
                }
                if (Validation.checkReportExist(lr, studentName, courseName, total)) {
                    lr.add(new Report(student.getStudentName(), studentName, total));
                }
            }
        }

        //print report
        for (int i = 0; i < lr.size(); i++) {
            System.out.printf("%-15s|%-10s|%-5d\n", lr.get(i).getStudentName(),
                    lr.get(i).getCourseName(), lr.get(i).getTotalCourse());
        }

    }


}
