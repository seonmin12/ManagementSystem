package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * {@code ParttimeVO} 클래스는 시간제 근무 직원 데이터를 저장하기 위한 모델 클래스입니다.
 * {@link EmployeeVO}를 상속받아 기본 직원 정보와 함께 시급, 근무 시간 등의 추가 정보를 제공합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParttimeVO extends EmployeeVO implements Comparable<ParttimeVO>{
    /** 시간제 근무 직원의 시급 */
    private int hourWage;
    /** 시간제 근무 직원의 근무 시간 */
    private int workHour;
    /** 시간제 근무 직원의 시급*근무 시간 */
    private int wage;

    /**
     * 이름, 사번, 시급, 근무 시간을 설정하는 생성자입니다.
     *
     * @param empNo 직원의 사번
     * @param name 직원의 이름
     * @param hourWage 시급
     * @param workHour 근무 시간
     */
    public ParttimeVO(String empNo, String name, int hourWage, int workHour) {
        super(name,empNo);
        this.hourWage = hourWage;
        this.workHour = workHour;
    }

    /**
     * 직원의 동등성을 비교합니다.
     *
     * @param o 비교할 객체
     * @return 동일한 사번을 가진 경우 {@code true}, 그렇지 않으면 {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        ParttimeVO that = (ParttimeVO) o;
        return Objects.equals(getEmpNo(), that.getEmpNo());
    }

    /**
     * 직원의 사번을 기준으로 정렬합니다.
     *
     * @param o 비교할 {@code ParttimeVO} 객체
     * @return 사번의 문자열 비교 결과
     */
    @Override
    public int compareTo(ParttimeVO o) {
        return this.getEmpNo().compareTo(o.getEmpNo());
    }

    /**
     * 시간제 근무 직원 정보를 문자열로 변환합니다.
     *
     * @return 포맷팅된 시간제 근무 직원 정보
     */
    @Override
    public String toString() {
        String str = "\t%-12s%-12s%-14d%-15d%-15d";
        return String.format(str, getEmpNo(), getName(), hourWage, workHour, wage);
    }
}
