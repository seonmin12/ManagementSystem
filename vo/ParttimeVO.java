package vo;

import lombok.Data;

@Data

public class ParttimeVO {
    private String name;
    private String empNo;
    private int hourWage; // 시급
    private int workHour; // 노동시간
    private int wage; // 임금

    ParttimeVO(String name, String empNo, int hourWage, int workHour) {


    }
}
