package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDHL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultHSS;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultHSSDao extends MongoRepository<ResultHSS, String> {
}
