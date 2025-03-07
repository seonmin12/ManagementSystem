package vo;

import lombok.Data;

@Data

public abstract class EmployeeVO {
    private String name;
    private String empNo;

    public EmployeeVO(){}

    public EmployeeVO(String name, String empNo) {
        this.name = name;
        this.empNo = empNo;
    }


}
