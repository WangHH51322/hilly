package cn.edu.cup.hilly.dataSource.mapper.mongo;

import cn.edu.cup.hilly.dataSource.model.mongo.project.HillyProject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HillyProjectDao extends MongoRepository<HillyProject, String> {
}
