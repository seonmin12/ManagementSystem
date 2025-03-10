package model;

import vo.EmployeeVO;
import vo.FulltimeVO;

/**
 * {@code Fulltime} 인터페이스는 정규직 직원 관리 기능을 정의하는 인터페이스입니다.
 * 입력, 수정, 월급 인상 계산 등 정규직 직원 관련 주요 기능을 제공합니다.
 */
public interface Fulltime extends DBCommon{
    /**
     * 정규직 직원 데이터의 월급 정보를 인상합니다.
     *
     * 저장 프로시저를 호출하여 모든 직원의 월급을 인상하고, 변경된 데이터를 데이터베이스에서 다시 읽어옵니다.
     * 월급 인상 실패 시 오류 메시지를 출력합니다.
     */
    void calcincreasesalary();


}
