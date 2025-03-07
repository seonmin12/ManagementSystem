package model;

import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

public interface DBCommon {
    void input(StudentVO newStudent);
    void input(FulltimeVO newFulltime);

    void input(ParttimeVO newParttime);

    void delete(String deleteNum);

    void update(StudentVO newStudent);
    void update(FulltimeVO newFulltime);

    //void update(ParttimeVO newParttime);

    void totalSearch();
    void search(String searchNum);

    void calculate();

    void sort(int sortNum);

}
