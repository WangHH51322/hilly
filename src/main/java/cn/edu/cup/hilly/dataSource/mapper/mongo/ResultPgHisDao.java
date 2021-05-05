package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultPgHis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultPgHisDao extends MongoRepository<ResultPgHis, String> {
}
