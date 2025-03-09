package model;

import vo.StudentVO;

public interface Student extends DBCommon {
    void total(StudentVO studentVO);
    void average(StudentVO studentVO);
    void grade(StudentVO studentVO);
}
