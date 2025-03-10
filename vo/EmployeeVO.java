package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code EmployeeVO} 클래스는 직원 데이터를 저장하기 위한 기본 모델 클래스입니다.
 * 사번과 이름 필드를 포함하며, {@link PersonVO}를 상속받습니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class EmployeeVO extends PersonVO {
    /** 직원의 사번 */
    private String empNo;

    /**
     * 이름과 사번 정보를 설정하는 생성자입니다.
     *
     * @param name 직원의 이름
     * @param empNo 직원의 사번
     */
    public EmployeeVO(String name, String empNo) {
        super(name);
        this.empNo = empNo;
    }
}
