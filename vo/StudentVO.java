package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * {@code StudentVO} 클래스는 학생 정보를 저장하기 위한 VO(Value Object) 클래스입니다.
 * 학생의 이름, 학번, 점수, 총점, 평균 및 학점을 저장합니다.
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
     * 학생 점수 입력 및 업데이트를 위한 생성자.
     * 지정한 정보로 {@code StudentVO} 인스턴스를 생성합니다.
     * <p>데이터베이스에서는 Student 테이블로 저장되며, 필드명은 컬럼명과 동일합니다.</p>
     *
     * @param sno     학생의 학번
     * @param name    학생의 이름
     * @param korean  학생의 국어 점수
     * @param english 학생의 영어 점수
     * @param math    학생의 수학 점수
     * @param science 학생의 과학 점수
     */
    public StudentVO(String sno, String name, int korean, int english, int math, int science) {
        super(name);
        this.sno = sno;
        this.korean = korean;
        this.english = english;
        this.math = math;
        this.science = science;

    }

    @Override
    public boolean equals(Object o) {
        StudentVO that = (StudentVO) o;
        return Objects.equals(sno, that.sno);
    }

    @Override
    public int compareTo(StudentVO o) {
        return this.sno.compareTo(o.sno);
    }

    @Override
    public String toString() {
        return "\t이름: " + getName() +
                "\t학번: " + sno +
                "\t국어: " + korean +
                "\t영어: " + english +
                "\t수학: " + math +
                "\t과학: " + science +
                "\t총점: " + total +
                "\t평균: " + average +
                "\t학점: " + grade;
    }
}
