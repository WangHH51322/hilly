package cn.edu.cup.hilly.dataSource.service;

import cn.edu.cup.hilly.dataSource.model.ExcelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author CodeChap
 * @date 2021-05-21 16:45
 */

@Service
@Transactional
public class FileService {

    @Autowired
    MongoTemplate mongoTemplate;

    public ExcelFile save(double[][] data, String hillyId,Integer inum, double[][] terrainData) {
        ExcelFile excelFile = new ExcelFile();
        excelFile.setHillyId(hillyId);
        excelFile.setLz(data);
        excelFile.setInum(inum);
        excelFile.setTerrainData(terrainData);
        ExcelFile save = mongoTemplate.save(excelFile, "file");
        return save;
    }

    public ExcelFile find(String id) {
        Query query = new Query(Criteria.where("hillyId").is(id));
        ExcelFile file = mongoTemplate.findOne(query, ExcelFile.class, "file");
        return file;
    }

    public double[][] findTerrainData(String id) {
        ExcelFile excelFile = find(id);
        double[][] terrainData = excelFile.getTerrainData();
        return terrainData;
    }
}
