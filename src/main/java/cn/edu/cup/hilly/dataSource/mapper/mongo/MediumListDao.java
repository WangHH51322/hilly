package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.mediumList.MediumList;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author CodeChap
 * @date 2021-05-16 16:21
 */
public interface MediumListDao extends MongoRepository<MediumList, String> {
}
