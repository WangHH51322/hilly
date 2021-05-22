package cn.edu.cup.hilly.dataSource.model.mongo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CodeChap
 * @date 2021-05-22 12:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "resultULocation")
public class ResultULocation {

    @Id
    private String _id;
    private String HillyId;

    private double[] uLocation;
}
