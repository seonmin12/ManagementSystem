package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
