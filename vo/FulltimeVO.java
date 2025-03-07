package vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FulltimeVO extends EmployeeVO {

    private int result; // 실적
    private char resultGrade; // 성과등급
    private int basicSalary; // 월급



    public FulltimeVO(String name, String empNo, int result, int basicSalary){
      super(name, empNo);
      this.result = result;
      this.basicSalary = basicSalary;
    }

    public FulltimeVO(){}


    @Override
    public String toString() {
        return "FulltimeVO{" +
                "이름='" + getName() + '\'' +
                ", 사번='" + getEmpNo() + '\'' +
                ", 실적=" + result +
                ", 성과등급=" + resultGrade +
                ", 월급=" + basicSalary +
                '}';

    }
}




