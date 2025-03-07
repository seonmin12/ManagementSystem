package vo;
import lombok.Data;

@Data
public class FulltimeVO {
    private String name;
    private String empNo;
    private int result; // 실적
    private char resultGrade; // 성과등급
    private int basicSalary; // 인상 전 월급
    private int rank; // 순위
    private double percentage; // 백분율
    private int salaryIncreaseAmount; // 인상된 월급 금액

    public FulltimeVO(String name, String empNo, int result, int basicSalary){
        // input, update시 사용
        // 생성자에서 필요로 하는 필드만 사용하여 DB 생성
        // DB 명은 Fulltime, 각 칼럼명은 필드명과 동일하게
        // 시간 남으면 파트타임 모델도 시도해보기
        this.name = name;
        this.empNo = empNo;
        this.result = result;
        this.basicSalary = basicSalary;
    }
}
