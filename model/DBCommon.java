package model;

import vo.FulltimeVO;
import vo.StudentVO;

public interface DBCommon {


    void delete(int deleteNum);




    void totalSearch();
    void search(int searchNum);



    void sort(int sortNum);

}
