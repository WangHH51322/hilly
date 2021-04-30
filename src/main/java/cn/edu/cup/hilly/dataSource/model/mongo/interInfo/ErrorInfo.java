package cn.edu.cup.hilly.dataSource.model.mongo.interInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ErrorInfo {

    private String name;
    private String unit;
    private String value;

    public ErrorInfo() {
        this.name = "错误提示";
        this.unit = "";
        this.value = "demo字符串";
    }

}
