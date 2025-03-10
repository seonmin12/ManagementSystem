package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * {@code FulltimeVO} 클래스는 정규직 직원 데이터를 저장하기 위한 모델 클래스입니다.
 * {@link EmployeeVO}를 상속받아 기본 직원 정보와 함께 실적과 월급과 같은 추가 정보를 제공합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FulltimeVO extends EmployeeVO implements Comparable<FulltimeVO>{
    /** 직원의 실적 */
    private int result;
    /** 직원의 기본 월급 */
    private int basicSalary;

    /**
     * 이름, 사번, 실적, 월급 정보를 설정하는 생성자입니다.
     *
     * @param empNo 직원의 사번
     * @param name 직원의 이름
     * @param result 직원의 실적
     * @param basicSalary 직원의 월급
     */
    public FulltimeVO(String empNo, String name, int result, int basicSalary){
      super(name, empNo);
      this.result = result;
      this.basicSalary = basicSalary;
    }

    /**
     * 직원의 동등성을 비교합니다.
     *
     * @param o 비교할 객체
     * @return 동일한 사번을 가진 경우 {@code true}, 그렇지 않으면 {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        // FulltimeVO 타입 체크, 객체 자기 자신과 비교
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
        FulltimeVO that = (FulltimeVO) o;
        return Objects.equals(getEmpNo(), that.getEmpNo());
    }

    /**
     * 직원의 사번을 기준으로 정렬합니다.
     *
     * @param o 비교할 {@code FulltimeVO} 객체
     * @return 사번의 문자열 비교 결과
     */
    @Override
    public int compareTo(FulltimeVO o) {
        return this.getEmpNo().compareTo(o.getEmpNo());
    }

    /**
     * 직원 정보를 문자열로 변환합니다.
     *
     * @return 포맷팅된 직원 정보
     */
    @Override
    public String toString() {
        String str = "\t%-13s%-14s%-13s%-20s";
        return String.format(str, getEmpNo(), getName(), result, basicSalary);
    }

}




