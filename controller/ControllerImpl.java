package controller;

import model.Fulltime;
import model.FulltimeDAO;
import model.StudentDAO;
import vo.FulltimeVO;
import vo.StudentVO;

public class ControllerImpl implements Controller {
    private StudentDAO student;
    private FulltimeDAO fulltimeEmp;
    private int selectModel = 0;

    private static ControllerImpl service;

    private ControllerImpl(){
        student = StudentDAO.getInstance();
        fulltimeEmp = FulltimeDAO.getInstance();
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
    public void delete(int deleteNum) {
        if (this.selectModel == 1) {
            student.delete(deleteNum);
        }else if(this.selectModel == 2){
            fulltimeEmp.delete(deleteNum);
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
    public void totalSearch() {
        if (this.selectModel == 1) {
            student.totalSearch();
        } else if (this.selectModel == 2) {
            fulltimeEmp.totalSearch();
        }
    }

    @Override
    public void search(int searchNum) {
        if (this.selectModel == 1) {
            student.search(searchNum);
        } else if (this.selectModel == 2) {
            fulltimeEmp.search(searchNum);
        }
    }

}
