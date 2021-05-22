package cn.edu.cup.hilly.dataSource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CodeChap
 * @date 2021-05-21 16:39
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelFile {

    @Id
    private String _id;

    private String hillyId;
    private double[][] lz;
    private Integer inum;
    private double[][] terrainData;
}
