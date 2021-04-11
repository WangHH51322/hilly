package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultDPLDao extends MongoRepository<ResultDPL, String> {
}
