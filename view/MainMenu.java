package view;

import controller.Controller;
import controller.ControllerImpl;
import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

import java.util.Scanner;

public class MainMenu {
    private Controller control;
    private Scanner scan;

    public MainMenu() {
        control = ControllerImpl.getInstance();
        scan = new Scanner(System.in);
    }
    void managementStart(){

        int choice;

        while(true){
            showMainMenu();
            choice = scan.nextInt();
            control.choice(choice);

            switch (choice){
                case 1 :
                    StudentSystem();
                    break;
                case 2 :
                    EmployeeSystem();
                    break;
                case 3 :
                    ParttimeSystem();
                case 4 :
                    System.out.println("시스템을 종료합니다.");
                default :
                    System.out.println("번호를 잘못입력하셨습니다.");
            }

        }
    }
    void showMainMenu(){
        System.out.println();
        System.out.println("\t------------------------------");
        System.out.println("\t-       매니지먼트 시스템       -");
        System.out.println("\t------------------------------");
        System.out.println("\t1. 학생관리\t\t2. 직원관리");
        System.out.println("\t3. 파트타임관리\t4. 시스템 종료");
        System.out.println("\t------------------------------");
        System.out.print("\t입력: ");
    }
    void StudentSystem() {

        int choice;


        while (true) {
            showStudentMenu();
            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    //학생입력
                    Sinput();
                    break;
                case 2:
                    //학생 삭제
                    Sdelete();
                    break;
                case 3:
                    //학생 수정
                    Supdate();
                    break;
                case 4:
                    //학생 명단
                    StotalSearch();
                    break;
                case 5:
                    //학생 검색
                    Ssearch();
                    break;
                case 6:
                    //학생 종료
                    exit();
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }
    }

    void showStudentMenu(){
        System.out.println();
        System.out.println("\t------------------------------");
        System.out.println("\t-       학생 관리 시스템       -");
        System.out.println("\t------------------------------");
        System.out.println("\t1. 학생 입력\t\t2. 학생 삭제");
        System.out.println("\t3. 학생 수정\t\t4. 학생 명단보기");
        System.out.println("\t5. 학생 검색\t\t6. 종료");
        System.out.println("\t------------------------------");
        System.out.print("\t입력: ");
    }
    void Sinput() {
        System.out.println("\t------------------------------");
        System.out.print("\t학번: ");
        String sno = scan.next();
        System.out.print("\t이름: ");
        String name = scan.next();
        System.out.print("\t국어 점수: ");
        int korean = scan.nextInt();
        System.out.print("\t영어 점수: ");
        int english = scan.nextInt();
        System.out.print("\t수학 점수: ");
        int math = scan.nextInt();
        System.out.print("\t과학 점수: ");
        int science = scan.nextInt();
        System.out.println("\t------------------------------");

        StudentVO studentVO = new StudentVO(sno,name,korean,english,math,science);
        control.input(studentVO);
    }
    void Sdelete(){
        System.out.println("\t------------------------------");
        System.out.print("\t삭제할 학번: ");
        String deleteNum = scan.next();
        System.out.println("\t------------------------------");

        control.delete(deleteNum);

    }
    void Supdate(){
        System.out.println("\t------------------------------");
        System.out.print("\t수정할 학번: ");
        String sno = scan.next();
        System.out.println("\t------------------------------");

        System.out.print("\t이름: ");
        String nname = scan.next();
        System.out.print("\t국어 점수: ");
        int nkorean = scan.nextInt();
        System.out.print("\t영어 점수: ");
        int nenglish = scan.nextInt();
        System.out.print("\t수학 점수: ");
        int nmath = scan.nextInt();
        System.out.print("\t과학 점수: ");
        int nscience = scan.nextInt();
        System.out.println("\t------------------------------");

        StudentVO studentVO = new StudentVO(sno,nname,nkorean,nenglish,nmath,nscience);
        control.update(studentVO);

    }
    void StotalSearch(){

        control.totalSearch();
    }
    void Ssearch(){

        System.out.println("\t------------------------------");
        System.out.print("\t검색할 학번: ");
        String sno = scan.next();
        System.out.println("\t------------------------------");

        control.search(sno);

    }
    void exit(){
        System.out.println("\t** 게시판 종료 **");
        System.exit(0);
    }


    void EmployeeSystem() {

        int choice;

        while (true) {
            System.out.println();
            System.out.println("\t------------------------------");
            System.out.println("\t-       직원 관리 시스템       -");
            System.out.println("\t------------------------------");
            System.out.println("\t1. 직원 입력\t\t2. 직원 삭제");
            System.out.println("\t3. 직원 수정\t\t4. 직원 명단보기");
            System.out.println("\t5. 직원 검색\t\t6. 종료");
            System.out.println("\t------------------------------");
            System.out.print("\t입력: ");
            choice = scan.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    //직원 입력
                    Einput();
                    break;
                case 2:
                    //직원 삭제
                    Edelete();
                    break;
                case 3:
                    //직원 수정
                    Eupdate();
                    break;
                case 4:
                    //직원 명단
                    EtotalSearch();
                    break;
                case 5:
                    //직원 검색
                    Esearch();
                    break;
                case 6:
                    //직원 종료
                    exit();
            }
        }

    }

    void Einput() {
        System.out.println("\t------------------------------");
        System.out.print("\t사번: ");
        String empNo = scan.next();
        System.out.print("\t이름: ");
        String name = scan.next();
        System.out.print("\t실적: ");
        int result = scan.nextInt();
        System.out.print("\t월급: ");
        int salary = scan.nextInt();
        System.out.println("\t------------------------------");

        FulltimeVO fulltimeVO = new FulltimeVO(empNo, name, result, salary);
        control.input(fulltimeVO);
    }

    void Edelete(){
        System.out.println("\t------------------------------");
        System.out.print("\t삭제할 사번: ");
        String deleteNum = scan.next();
        System.out.println("\t------------------------------");

        control.delete(deleteNum);
    }

    void Eupdate(){
        System.out.println("\t------------------------------");
        System.out.print("\t수정할 사번: ");
        String eno = scan.next();
        System.out.println("\t------------------------------");

        System.out.print("\t이름: ");
        String name = scan.next();
        System.out.print("\t실적: ");
        int result = scan.nextInt();
        System.out.print("\t월급: ");
        int basicSalary = scan.nextInt();
        System.out.println("\t------------------------------");

        FulltimeVO fulltimeVO = new FulltimeVO(name, eno, result, basicSalary);
        control.update(fulltimeVO);
    }
    void EtotalSearch(){
        control.totalSearch();
    }

    void Esearch(){
        System.out.println("\t------------------------------");
        System.out.print("\t검색할 사번: ");
        String searchNum = scan.next();  // 사용자 입력 받기
        System.out.println("\t------------------------------");

        control.search(searchNum);
    }

    void ParttimeSystem() {
        int choice;

        while (true) {
            System.out.println();
            System.out.println("\t------------------------------");
            System.out.println("\t-       파트타임 관리 시스템       -");
            System.out.println("\t------------------------------");
            System.out.println("\t1. 파트타임 입력\t\t2. 파트타임 삭제");
            System.out.println("\t3. 파트타임 수정\t\t4. 파트타임 명단보기");
            System.out.println("\t5. 파트타임 검색\t\t6. 종료");
            System.out.println("\t------------------------------");
            System.out.print("\t입력: ");
            choice = scan.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    //직원 입력
                    Pinput();
                    break;
                case 2:
                    //직원 삭제
                    Pdelete();
                    break;
                case 3:
                    //직원 수정
                    Pupdate();
                    break;
                case 4:
                    //직원 명단
                    PtotalSearch();
                    break;
                case 5:
                    //직원 검색
                    Psearch();
                    break;
                case 6:
                    //직원 종료
                    exit();
            }
        }
    }
    void Pinput() {
        System.out.println("\t------------------------------");
        System.out.print("\t파트타임 번호: ");
        String pno = scan.next();
        System.out.print("\t이름: ");
        String name = scan.next();
        System.out.print("\t최저시급: ");
        int wage = scan.nextInt();
        System.out.print("\t시간: ");
        int hour = scan.nextInt();
        System.out.println("\t------------------------------");

        ParttimeVO parttimeVO = new ParttimeVO(pno, name, wage, hour);
        control.input(parttimeVO);
    }

    void Pdelete(){
        System.out.println("\t------------------------------");
        System.out.print("\t삭제할 파트타임 번호: ");
        String deleteNum = scan.next();
        System.out.println("\t------------------------------");

        control.delete(deleteNum);
    }

    void Pupdate(){
        System.out.println("\t------------------------------");
        System.out.print("\t수정할 파트타임 번호: ");
        String pno = scan.next();
        System.out.println("\t------------------------------");

        System.out.print("\t이름: ");
        String name = scan.next();
        System.out.print("\t최저시급: ");
        int wage = scan.nextInt();
        System.out.print("\t시간: ");
        int hour = scan.nextInt();
        System.out.println("\t------------------------------");

        ParttimeVO parttimeVO = new ParttimeVO(name, pno, wage, hour);
        control.update(parttimeVO);
    }
    void PtotalSearch(){
        control.totalSearch();
    }

    void Psearch(){
        System.out.println("\t------------------------------");
        System.out.print("\t검색할 파트타임 번호: ");
        String searchNum = scan.next();  // 사용자 입력 받기
        System.out.println("\t------------------------------");

        control.search(searchNum);
    }


    public static void main(String[] args) {
        new MainMenu().managementStart();
    }

}
