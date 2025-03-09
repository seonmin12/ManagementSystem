package controller;

import vo.PersonVO;

public interface Controller {
    void choice(int choiceNum);
    void input(PersonVO personVO);
    void delete(String deleteNum);
    void update(PersonVO newPerson);
    void totalSearch(int sortNum);
    void search(String searchNum);
}
