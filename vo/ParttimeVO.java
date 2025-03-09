package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParttimeVO extends EmployeeVO implements Comparable<ParttimeVO>{
    private int hourWage; // 시급
    private int workHour; // 노동시간
    private int wage; // 임금

    public ParttimeVO(String empNo, String name, int hourWage, int workHour) {
        super(name,empNo);
        this.hourWage = hourWage;
        this.workHour = workHour;
    }

    @Override
    public boolean equals(Object o) {
        ParttimeVO that = (ParttimeVO) o;
        return Objects.equals(getEmpNo(), that.getEmpNo());
    }

    @Override
    public int compareTo(ParttimeVO o) {
        return this.getEmpNo().compareTo(o.getEmpNo());
    }

    @Override
    public String toString() {
        String str = "\t%-12s%-12s%-14d%-15d%-15d";
        return String.format(str, getEmpNo(), getName(), hourWage, workHour, wage);
    }
}
