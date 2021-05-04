package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultSimple;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultSimpleDao extends MongoRepository<ResultSimple, String> {
}
