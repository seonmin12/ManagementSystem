package model;

import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

public interface Parttime extends DBCommon{
    void input(ParttimeVO newParttime);
    void update(ParttimeVO newParttime);
    void calcWage();
}
