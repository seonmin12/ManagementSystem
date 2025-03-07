package controller;

import model.Fulltime;
import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

public interface Controller {
    void choice(int choiceNum);

    void input(StudentVO newStudent);
    void input(FulltimeVO newFulltime);

    void input(ParttimeVO parttimeVO);

    void delete(String deleteNum);

    void update(StudentVO newStudent);
    void update(FulltimeVO newFulltime);

    void update(ParttimeVO newParttime);

    void totalSearch();
    void search(String searchNum);

}
