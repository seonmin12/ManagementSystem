package vo;

public class FulltimeVO {
    private String name;
    private String empNo;
    private int result; // 실적
    private char resultGrade; // 성과등급
    private int basicSalary; // 인상 전 월급
    private int rank; // 순위
    private double percentage; // 백분율
    private int salaryIncreaseAmount; // 인상된 월급 금액

    FulltimeVO(String name, String empNo, int result, int basicSalary){
        // input, update시 사용
    }
}
