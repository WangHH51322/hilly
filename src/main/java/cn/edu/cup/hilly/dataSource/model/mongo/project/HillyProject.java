package cn.edu.cup.hilly.dataSource.model.mongo.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class HillyProject {

    @Id
    private String _id;
    private String projectName;
    private Date createDate;
    private Date changeDate;
    private String author;
    private String remarks;
    private String hillyId;
}
