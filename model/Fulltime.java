package model;

import vo.FulltimeVO;

public interface Fulltime extends DBCommon{
    void assignGrade(FulltimeVO fulltimeVO);
    void calcRankAndPercent(FulltimeVO fulltimeVO);
    void calcSalaryIncrease(FulltimeVO fulltimeVO);
    void input(FulltimeVO newFulltime);
    void update(FulltimeVO newFulltime);


}
