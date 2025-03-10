package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * {@code StudentVO} 클래스는 학생 데이터를 저장하기 위한 모델 클래스입니다.
 * {@link PersonVO}를 상속받아 기본 학생 정보와 함께 학번, 과목 점수, 총점 등 추가 정보를 제공합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVO extends PersonVO implements Comparable<StudentVO>{
    /** 학번 */
    private String sno;
    /** 국어 점수 */
    private int korean;
    /** 영어 점수 */
    private int english;
    /** 수학 점수 */
    private int math;
    /** 과학 점수 */
    private int science;
    /** 총점 */
    private int total;
    /** 평균 */
    private float average;
    /** 학점 */
    private String grade;

    /**
     * 학번, 이름, 국어, 영어, 수학, 과학 점수를 설정하는 생성자입니다.
     *
     * @param sno 학생의 학번
     * @param name 학생의 이름
     * @param korean 국어 점수
     * @param english 영어 점수
     * @param math 수학 점수
     * @param science 과학 점수
     */
    public StudentVO(String sno, String name, int korean, int english, int math, int science) {
        super(name);
        this.sno = sno;
        this.korean = korean;
        this.english = english;
        this.math = math;
        this.science = science;
    }

    /**
     * 학생의 동등성을 비교합니다.
     *
     * @param o 비교할 객체
     * @return 동일한 학번을 가진 경우 {@code true}, 그렇지 않으면 {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        StudentVO that = (StudentVO) o;
        return Objects.equals(sno, that.sno);
    }

    /**
     * 학생의 학번을 기준으로 정렬합니다.
     *
     * @param o 비교할 {@code StudentVO} 객체
     * @return 학번의 문자열 비교 결과
     */
    @Override
    public int compareTo(StudentVO o) {
        return this.sno.compareTo(o.sno);
    }

    /**
     * 학생 정보를 문자열로 변환합니다.
     *
     * @return 포맷팅된 학생 정보
     */
    @Override
    public String toString() {
        String str = "\t%-12s%-11s%-11d%-11d%-11d%-11d%-11d%-12.1f%-8s";
        return String.format(str, sno, getName(), korean, english, math, science, total, average, grade);
    }
}
