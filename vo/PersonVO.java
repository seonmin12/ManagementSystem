package vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class PersonVO {
    private String name;

    public PersonVO(String name){
        this.name = name;
    }
}
