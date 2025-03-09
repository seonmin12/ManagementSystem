package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class EmployeeVO extends PersonVO {
    private String empNo;

    public EmployeeVO(String name, String empNo) {
        super(name);
        this.empNo = empNo;
    }
}
