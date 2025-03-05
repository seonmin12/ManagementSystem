package model;

import vo.FulltimeVO;
import vo.StudentVO;

public interface DBCommon {
    void input(StudentVO newStudent);
    void input(FulltimeVO newFulltime);

    void delete(int deleteNum);

    void update(StudentVO newStudent);
    void updqte(FulltimeVO newFulltime);

    void totalSearch();
    void search(int searchNum);

    void calculate();

    void sort(int sortNum);

}
