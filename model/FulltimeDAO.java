package model;

import vo.FulltimeVO;
import vo.StudentVO;

import java.util.ArrayList;

public class FulltimeDAO implements Fulltime  {
    private static FulltimeDAO dao;

    private FulltimeDAO(){ }

    public static FulltimeDAO getInstance(){
        if(dao==null) dao = new FulltimeDAO();

        return dao;
    }

    private ArrayList<FulltimeVO> fulltimeList;

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
    public void update(FulltimeVO newFulltime) {

    }

    @Override
    public void totalSearch() {

    }

    @Override
    public void search(int searchNum) {

    }

    @Override
    public void calculate() {

    }

    @Override
    public void sort(int sortNum) {

    }

    @Override
    public void assignGrade() {

    }

    @Override
    public void calcRankAndPercent() {

    }

    @Override
    public void calcSalaryIncrease() {

    }
}
