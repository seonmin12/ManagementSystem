package vo;

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

    StudentVO(String name, String sno, int korean, int english, int math, int science) {
        // input, update시 사용
    }

}
