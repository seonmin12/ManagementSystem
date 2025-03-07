package model;

import vo.EmployeeVO;
import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

public interface Parttime extends DBCommon{
    void input(EmployeeVO newEmployee);
    void update(EmployeeVO newEmployee);

}
