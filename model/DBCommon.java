package model;

import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.StudentVO;

public interface DBCommon {
    void delete(String deleteNum);
    void totalSearch();
    void search(String searchNum);
    void sort(int sortNum);
}
