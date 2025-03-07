package controller;

import model.Fulltime;
import model.FulltimeDAO;
import model.ParttimeDAO;
import model.StudentDAO;
import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

import java.util.Scanner;

public class ControllerImpl implements Controller {
    private StudentDAO student;
    private FulltimeDAO fulltimeEmp;
    private ParttimeDAO parttimeEmp;
    private int selectModel = 0;

    private static ControllerImpl service;

    private ControllerImpl(){
        student = StudentDAO.getInstance();
        fulltimeEmp = FulltimeDAO.getInstance();
        parttimeEmp = ParttimeDAO.getInstance();
    }

    public static ControllerImpl getInstance(){
        if(service==null) service = new ControllerImpl();

        return service;
    }

    @Override
    public void choice(int choiceNum) {
        //System.out.println("choice선택");
        switch (choiceNum){
            case 1:
                System.out.println("\t학생관리");
                this.selectModel = 1;
                break;
            case 2:
                System.out.println("\t직원관리");
                this.selectModel = 2;
                break;
            case 3:
                this.selectModel = 3;
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("\t잘못된 입력입니다. 1, 2, 3 중 하나를 선택하세요.");
                break;
        }
    }

    @Override
    public void input(StudentVO newStudent) {
        student.input(newStudent);
    }

    @Override
    public void input(FulltimeVO newFulltime) {
        fulltimeEmp.input(newFulltime);
    }

    @Override
    public void input(ParttimeVO newParttime) {
        parttimeEmp.input(newParttime);
    }

    @Override
    public void delete(String deleteNum) {
        if (this.selectModel == 1) {
            student.delete(deleteNum);
        }else if(this.selectModel == 2){
            fulltimeEmp.delete(deleteNum);
        } else if (this.selectModel == 3) {
            parttimeEmp.delete(deleteNum);
        }
    }

    @Override
    public void update(StudentVO newStudent) {
        student.update(newStudent);
    }

    @Override
    public void update(FulltimeVO newFulltime) {
        fulltimeEmp.update(newFulltime);
    }

    @Override
    public void update(ParttimeVO newParttime) {
        parttimeEmp.update(newParttime);
    }

    @Override
    public void totalSearch() {
        if (this.selectModel == 1) {
            student.totalSearch();
        } else if (this.selectModel == 2) {
            fulltimeEmp.totalSearch();
            System.out.println("\t------------------------------\n" +
                    "\t전체 직원의 월급을 인상하시겠습니까? (y/n) : ");
            if (new Scanner(System.in).next().equals("y")){
                // 월급 인상
            }

        } else if (this.selectModel == 3) {
            parttimeEmp.totalSearch();
        }
    }

    @Override
    public void search(String searchNum) {
        if (this.selectModel == 1) {
            student.search(searchNum);
        } else if (this.selectModel == 2) {
            fulltimeEmp.search(searchNum);
        } else if (this.selectModel == 3) {
            parttimeEmp.search(searchNum);
        }
    }

}
