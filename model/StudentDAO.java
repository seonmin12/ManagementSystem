package model;

import vo.FulltimeVO;
import vo.StudentVO;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO implements Student {
    private static StudentDAO dao;

    private StudentDAO(){ }

    public static StudentDAO getInstance(){
        if(dao==null) dao = new StudentDAO();

        return dao;
    }

    private ArrayList<StudentVO>studentlist;






    @Override
    public void delete(String deleteNum) {


    }

    @Override
    public void update(StudentVO newStudent) {

    }


    @Override
    public void totalSearch() {

    }

    @Override
    public void search(String searchNum) {

    }

    @Override
    public void sort(int sortNum) {

    }

    @Override
    public void total() {

    }

    @Override
    public void avg() {

    }

    @Override
    public void grade() {

    }
    @Override
    public void input(StudentVO newStudent){

    }
}
