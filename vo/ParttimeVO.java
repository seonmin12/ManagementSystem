package vo;

public class ParttimeVO {
    String empNo;
    String name;
    int hourWage;
    int workHour;

    public ParttimeVO(String empNo, String name, int hourWage, int workHour){
       this.empNo = empNo;
       this.name = name;
       this.hourWage = hourWage;
       this.workHour = workHour;
    }
}
