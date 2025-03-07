package controller;

import model.Fulltime;
import vo.FulltimeVO;
import vo.StudentVO;

public interface Controller {
    void choice(int choiceNum);

    void input(StudentVO newStudent);
    void input(FulltimeVO newFulltime);

    void delete(int deleteNum);

    void update(StudentVO newStudent);
    void update(FulltimeVO newFulltime);

    void totalSearch();
    void search(int searchNum);

}
