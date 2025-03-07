package vo;

import lombok.Data;

@Data
public class StudentVO {
    /** 이름 */
    private String name;
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
        // input, update시 사용
        // 생성자에서 필요로 하는 필드만 사용하여 DB 생성
        // DB 명은 Fulltime, 각 칼럼명은 필드명과 동일하게
        this.name = name;
        this.sno = sno;
        this.korean = korean;
        this.english = english;
        this.math = math;
        this.science = science;

    }

    public StudentVO() {

    }
}
