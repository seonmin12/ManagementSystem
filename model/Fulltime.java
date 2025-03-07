package model;

import vo.EmployeeVO;
import vo.FulltimeVO;

public interface Fulltime extends DBCommon{
//    void assignGrade(FulltimeVO fulltimeVO);
//    void calcRankAndPercent(FulltimeVO fulltimeVO);
//    void calcSalaryIncrease(FulltimeVO fulltimeVO);
    void input(EmployeeVO employeeVO);
    void update(EmployeeVO employeeVO);
    void calcincreasesalary(EmployeeVO newEmployee);


}
