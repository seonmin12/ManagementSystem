package model;

import vo.StudentVO;

public interface Student extends DBCommon {
    void total();
    void avg();
    void grade();
    void input(StudentVO newStudent);
    void update(StudentVO newStudent);

}
