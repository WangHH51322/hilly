package cn.edu.cup.hilly.dataSource.model.mongo.mediumList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@Document
public class MediumList {
    @Id
    private String _id;

    private String name;
    private String unit;
    private List<Medium> value;
    public MediumList() {
        this.name = "流体介质列表";
        this.unit = "";
        this.value = new ArrayList<>();
        value.add(new Medium());
    }
}
