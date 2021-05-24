package cn.edu.cup.hilly.dataSource.service;

import cn.edu.cup.hilly.dataSource.model.ExcelFile;
import com.mongodb.client.result.DeleteResult;
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

    public ExcelFile insert(double[][] data, String hillyId,Integer inum, double[][] terrainData) {
        ExcelFile fileOld = find(hillyId);
        if (fileOld == null) {
            ExcelFile save = add(data, hillyId ,inum, terrainData);
            return save;
        } else {
            remove(hillyId);
            ExcelFile save = add(data, hillyId ,inum, terrainData);
            return save;
        }
    }

    public ExcelFile add(double[][] data, String hillyId,Integer inum, double[][] terrainData) {
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

    public long remove(String id) {
        Query query = new Query(Criteria.where("hillyId").is(id));
        DeleteResult file = mongoTemplate.remove(query, ExcelFile.class, "file");
        long deletedCount = file.getDeletedCount();
        return deletedCount;
    }

    public double[][] findTerrainData(String id) {
        ExcelFile excelFile = find(id);
        double[][] terrainData = excelFile.getTerrainData();
        return terrainData;
    }
}
