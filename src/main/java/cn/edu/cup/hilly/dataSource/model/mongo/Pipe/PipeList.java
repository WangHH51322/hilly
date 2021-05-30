package cn.edu.cup.hilly.dataSource.model.mongo.Pipe;

import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.Medium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@Document
public class PipeList {
    @Id
    private String _id;

    private String name;
    private String unit;
    private List<Pipe> value;
    public PipeList() {
        this.name = "管段列表";
        this.unit = "";
        this.value = new ArrayList<>();
        value.add(new Pipe());
    }
}
