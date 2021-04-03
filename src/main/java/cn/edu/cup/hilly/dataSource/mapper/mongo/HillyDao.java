package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.Hilly;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HillyDao extends MongoRepository<Hilly, String> {

}