package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultDPL;
import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultLgHis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultLgHisDao extends MongoRepository<ResultLgHis, String> {
}
