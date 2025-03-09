package controller;

import model.FulltimeDAO;
import model.ParttimeDAO;
import model.StudentDAO;
import vo.PersonVO;

import java.util.Scanner;


/**
 * {@code ControllerImpl} 클래스는 {@link Controller} 인터페이스를 구현하는 클래스입니다.
 * 이 클래스는 학생, 정규직 직원, 시간제 직원 관리를 위한 다양한 기능을 제공합니다.
 */
public class ControllerImpl implements Controller {

    /**
     * 학생 정보를 관리하는 DAO 객체
     */
    private StudentDAO student;

    /**
     * 정규직 직원 정보를 관리하는 DAO 객체
     */
    private FulltimeDAO fulltimeEmp;

    /**
     * 시간제 직원 정보를 관리하는 DAO 객체
     */
    private ParttimeDAO parttimeEmp;

    /**
     * 현재 선택된 모델 (1: 학생, 2: 정규직 직원, 3: 시간제 직원)
     */
    private int selectModel = 0;

    /**
     * Singleton 패턴을 위한 ControllerImpl 객체
     */
    private static ControllerImpl service;

    /**
     * ControllerImpl의 생성자.
     * Singleton 패턴을 위해 private으로 선언되었습니다.
     */
    private ControllerImpl(){ }

    /**
     * Singleton 패턴으로 인스턴스를 반환하는 메서드.
     *
     * @return {@code ControllerImpl}의 Singleton 인스턴스
     */
    public static ControllerImpl getInstance(){
        if(service==null) service = new ControllerImpl();

        return service;
    }

    /**
     * 사용자가 선택한 메뉴 번호에 따라 어떤 모델을 관리할지 {@code selectModel}변수를 설정하는 메서드입니다.
     *
     * @param choiceNum 선택된 메뉴 번호 (1: 학생, 2: 정규직 직원, 3: 시간제 직원, 4: 종료)
     */
    @Override
    public void choice(int choiceNum) {
        //System.out.println("choice선택");
        switch (choiceNum){
            case 1:
                this.selectModel = 1;
                student = StudentDAO.getInstance();
                break;
            case 2:
                this.selectModel = 2;
                fulltimeEmp = FulltimeDAO.getInstance();
                break;
            case 3:
                this.selectModel = 3;
                parttimeEmp = ParttimeDAO.getInstance();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("\t잘못된 입력입니다. 1, 2, 3 중 하나를 선택하세요.");
                break;
        }
    }


    @Override
    public void input(PersonVO personVO) {
        if (this.selectModel == 1) {
            student.input(personVO);
        }else if(this.selectModel == 2){
            fulltimeEmp.input(personVO);
        } else if (this.selectModel == 3) {
            parttimeEmp.input(personVO);
        }
    }

    /**
     * 현재 선택된 모델(학생/정규직/시간제)에 따라 지정된 데이터를 삭제합니다.
     *
     * @param deleteNum 삭제할 데이터의 ID
     */
    @Override
    public void delete(String deleteNum) {
        if (this.selectModel == 1) {
            student.delete(deleteNum);
        }else if(this.selectModel == 2){
            fulltimeEmp.delete(deleteNum);
        } else if (this.selectModel == 3) {
            parttimeEmp.delete(deleteNum);
        }
    }

    @Override
    public void update(PersonVO personVO) {
        if (this.selectModel == 1) {
            student.update(personVO);
        }else if(this.selectModel == 2){
            fulltimeEmp.update(personVO);
        } else if (this.selectModel == 3) {
            parttimeEmp.update(personVO);
        }
    }

    /**
     * 현재 선택된 모델(학생/정규직/시간제)에 해당하는 모든 데이터를 검색합니다.
     * TODO 내용 수정 필요
     * 정규직 데이터를 검색할 경우, 월급 인상 여부에 대한 추가 입력을 받습니다.
     */
    @Override
    public void totalSearch(int sortNum) {
        if (this.selectModel == 1) {
            student.totalSearch(sortNum);
        } else if (this.selectModel == 2) {
            fulltimeEmp.totalSearch(sortNum);
            System.out.print("\t------------------------------\n" +
                    "\t전체 직원의 월급을 인상하시겠습니까? (y/n) : ");
            if (new Scanner(System.in).next().equals("y")){
                fulltimeEmp.calcincreasesalary();
            }

        } else if (this.selectModel == 3) {
            parttimeEmp.totalSearch(sortNum);
        }
    }

    /**
     * 현재 선택된 모델(학생/정규직/시간제)에 따라 지정된 데이터를 검색합니다.
     *
     * @param searchNum 검색할 데이터의 ID
     */
    @Override
    public void search(String searchNum) {
        if (this.selectModel == 1) {
            student.search(searchNum);
        } else if (this.selectModel == 2) {
            fulltimeEmp.search(searchNum);
        } else if (this.selectModel == 3) {
            parttimeEmp.search(searchNum);
        }
    }
}
