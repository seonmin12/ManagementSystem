package model;

import vo.EmployeeVO;
import vo.FulltimeVO;

/**
 * {@code Fulltime} 인터페이스는 정규직 직원의 관리 기능을 정의하는 인터페이스입니다.
 * 입력, 수정, 월급 인상 계산 등의 기능을 제공합니다.
 */
public interface Fulltime extends DBCommon{
    /**
     * 모든 정규직 직원의 월급을 인상하는 메서드입니다.
     */
    void calcincreasesalary();


}
