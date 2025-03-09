package model;

import vo.PersonVO;

public interface DBCommon {
    void input(PersonVO personVO);
    void delete(String deleteNum);
    void update(PersonVO personVO);
    void totalSearch(int sortNum);
    void search(String searchNum);
    void sort(int sortNum);
}
