package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import cn.edu.cup.hilly.dataSource.model.mongo.result.KeyPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyPointDao extends MongoRepository<KeyPoint, String> {

}