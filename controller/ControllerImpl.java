package controller;

import model.Fulltime;
import model.FulltimeDAO;
import model.StudentDAO;
import vo.FulltimeVO;
import vo.StudentVO;

public class ControllerImpl implements Controller {
    private StudentDAO student;
    private FulltimeDAO fulltimeEmp;

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

    }

    @Override
    public void input(StudentVO newStudent) {

    }

    @Override
    public void input(FulltimeVO newFulltime) {

    }

    @Override
    public void delete(int deleteNum) {

    }

    @Override
    public void update(StudentVO newStudent) {

    }

    @Override
    public void update(Fulltime newFulltime) {

    }

    @Override
    public void totalSearch() {

    }

    @Override
    public void Search(int searchNum) {

    }
}
