import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Manager {


    //show menu
    public static void menu() {
        System.out.println("1. Create");
        System.out.println("2. Find and Sort");
        System.out.println("3. Update/Delete");
        System.out.println("4. Report");
        System.out.println("5. Update/Export Excel");
        System.out.println("6. Display List Student");
        System.out.println("7. Exit");
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
            Collections.sort(listStudentFindByName, new Student());
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
            if (student.getStudentName().toLowerCase().contains(name.toLowerCase())) {
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
        System.out.print("Enter ID to search: ");
        String id = Validation.checkInputString();
        ArrayList<Student> listStudentUpdate = getListStudentById(ls, id);

        if (listStudentUpdate.isEmpty()) {
            System.err.println("Not found student.");
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
                    System.out.println("Update success.");
                }
            } else {
                ls.remove(student);
                count--;
                System.out.println("Delete success.");
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

    //get list Student from file Excel
    public static void exportDataExcelToArray(ArrayList<Student> ls) throws IOException {
        File file = new File("C:\\Users\\dangd\\Downloads\\test.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);


        //get All rows in file excel
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
        boolean check = false;
        for (int i = 0; i < rowCount; i++) {
            int cellCount = sheet.getRow(i).getLastCellNum();
            String id = null, name = null, semester = null, course = null;
            for (int j = 0; j < cellCount; j++) {
                if (j == 0) {
                    id = sheet.getRow(i).getCell(j).getStringCellValue();
                }
                if (j == 1) {
                    name = sheet.getRow(i).getCell(j).getStringCellValue();
                }
                if (j == 2) {
                    semester = sheet.getRow(i).getCell(j).getStringCellValue();
                }
                if (j == 3) {
                    course = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }
            if (Validation.checkStudentExist(ls, id, name, semester, course)) {
                ls.add(new Student(id, name, semester, course));
                check = true;
            }
        }
        if (check) {
            System.out.println("Extract file Excel success!");
        }
        wb.close();
    }

    //delete All Rows In File Excel
    public static void deleteAllDataExcel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\dangd\\Downloads\\test.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(1);

        int rowCount = sheet.getLastRowNum()+1;
        System.out.println(rowCount);
        for (int i = 0; i < rowCount; i++) {
            if (sheet.getRow(i)!= null){
                sheet.removeRow(sheet.getRow(i));
            }
        }
        //Save file Excel
        FileOutputStream outFile = new FileOutputStream("C:\\Users\\dangd\\Downloads\\test.xlsx");
        workbook.write(outFile);
        workbook.close();
        System.out.println("Delete data success!");

    }

    //Extract Data from listStudent to Excel
    public static void exportDataArrayToExcel(ArrayList<Student> ls) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\dangd\\Downloads\\test.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(1);

        for (int i = 0; i < ls.size(); i++) {
            int totalCell = 4;
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < totalCell; j++) {
                if (j == 0) {
                    row.createCell(j).setCellValue(ls.get(i).getId());
                }
                if (j == 1) {
                    row.createCell(j).setCellValue(ls.get(i).getStudentName());
                }
                if (j == 2) {
                    row.createCell(j).setCellValue(ls.get(i).getSemester());
                }
                if (j == 3) {
                    row.createCell(j).setCellValue(ls.get(i).getCourseName());
                }

            }
        }

        //Save file Excel
        System.out.println("Extract to Excel success!");
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\dangd\\Downloads\\test.xlsx");
        workbook.write(fileOutputStream);
        workbook.close();
    }


    //menu manager excel
    public static void menuManagerExcel(ArrayList<Student>ls) throws IOException {
        System.out.print("You want to Update (u/U) or Export (e/E): ");
        if (Validation.checkInputUE()){
            exportDataExcelToArray(ls);
        }else {
            exportDataArrayToExcel(ls);
        }
    }

    //print Report
    public static void report(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List Empty.");
            return;
        }
        ArrayList<Report> lr = new ArrayList<>();
        for (Student student : ls) {
            int total = 0;
            String id = student.getId();
            String studentName = student.getStudentName();
            String courseName = student.getCourseName();
            for (Student studentCountTotal : ls) {
                if (id.equalsIgnoreCase(studentCountTotal.getId())
                        && courseName.equalsIgnoreCase(studentCountTotal.getCourseName())) {
                    total++;
                }
            }
            if (Validation.checkReportExist(lr, studentName, courseName, total)) {
                lr.add(new Report(student.getStudentName(), student.getCourseName(), total));
            }
        }

        //print report

        lr.sort(Comparator.comparing(Report::getStudentName));
//        lr.sort(Comparator.comparing(p->p.getStudentName())); //java 8, lambda
        System.out.printf("%-5s%-18s%-10s%-11s\n", "STT", "StudentName", "Course", "TotalCourse");
        int count = 0;
        for (Report report : lr) {
            count++;
            System.out.printf("%-5d%-18s%-10s%11d\n", count, report.getStudentName(),
                    report.getCourseName(), report.getTotalCourse());
        }
        System.out.println();

    }

    //print list student
    public static void listStudent(ArrayList<Student>ls){
        System.out.println("=====List Student Updated/Delete====");
        for (Student student : ls) {
            student.printStudent();
        }
    }


}
