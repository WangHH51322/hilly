package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.result.ResultAllLineFP;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultALFPDao extends MongoRepository<ResultAllLineFP, String> {
}
