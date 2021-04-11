package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.result.TempTest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TempTestDao extends MongoRepository<TempTest, String> {
}
