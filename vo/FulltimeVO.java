package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FulltimeVO extends EmployeeVO implements Comparable<FulltimeVO>{

    private int result; // 실적
    private int basicSalary; // 월급


    public FulltimeVO(String name, String empNo, int result, int basicSalary){
      super(name, empNo);
      this.result = result;
      this.basicSalary = basicSalary;
    }

    @Override
    public boolean equals(Object o) {
        FulltimeVO that = (FulltimeVO) o;
        return Objects.equals(getEmpNo(), that.getEmpNo());
    }

    @Override
    public int compareTo(FulltimeVO o) {
        return this.getEmpNo().compareTo(o.getEmpNo());
    }

    @Override
    public String toString() {
        return "\t이름: " + getName() +
                "\t사번: " + getEmpNo() +
                "\t실적: " + result +
                "\t월급: " + basicSalary;
    }

}




