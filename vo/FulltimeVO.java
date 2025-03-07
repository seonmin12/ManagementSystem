package vo;

import lombok.Data;

@Data

public class FulltimeVO {
    private String name;
    private String empNo;
    private int result; // 실적
    private char resultGrade; // 성과등급
    private int basicSalary; // 월급
    private int rank; // 순위
    private double percentage; // 백분율


    FulltimeVO(String name, String empNo, int result, int basicSalary){
        // input, update시 사용
        // 생성자에서 필요로 하는 필드만 사용하여 DB 생성
        // DB 명은 Fulltime, 각 칼럼명은 필드명과 동일하게
        // 시간 남으면 파트타임 모델도 시도해보기
    }

    public FulltimeVO(){}


    @Override
    public String toString() {
        return "FulltimeVO{" +
                "이름='" + name + '\'' +
                ", 사번='" + empNo + '\'' +
                ", 실적=" + result +
                ", 성과등급=" + resultGrade +
                ", 월급=" + basicSalary +
                ", 순위=" + rank +
                ", 백분율=" + percentage +
                '}';
    }
}




