package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.stationList.StationPumps;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author CodeChap
 * @date 2021-05-16 22:24
 */
public interface StationPumpsDao extends MongoRepository<StationPumps, String> {
}
