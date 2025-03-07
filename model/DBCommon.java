package model;

import vo.FulltimeVO;
import vo.StudentVO;

public interface DBCommon {
    void input(StudentVO newStudent);
    void input(FulltimeVO newFulltime);

    void delete(String deleteNum);

    void update(StudentVO newStudent);
    void update(FulltimeVO newFulltime);

    void totalSearch();
    void search(String searchNum);

    void calculate();

    void sort(int sortNum);

}
