package vo;

import lombok.Data;

@Data

public class ParttimeVO extends EmployeeVO {

    private int hourWage; // 시급
    private int workHour; // 노동시간
    private int wage; // 임금

    ParttimeVO(String name, String empNo, int hourWage, int workHour) {
        super(name,empNo);
        this.hourWage = hourWage;
        this.workHour = workHour;



    }
    public ParttimeVO(){}

    @Override
    public String toString() {
        return "ParttimeVO{" +
                "이름='" + getName() + '\'' +
                ", 사번='" + getEmpNo() + '\'' +
                ", 시급=" + hourWage +
                ", 노동시간=" + workHour +
                ", 임금=" + wage +
                '}';
    }
}
