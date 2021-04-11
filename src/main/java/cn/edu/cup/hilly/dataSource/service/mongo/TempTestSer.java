package cn.edu.cup.hilly.dataSource.service.mongo;

import cn.edu.cup.hilly.dataSource.mapper.mongo.TempTestDao;
import cn.edu.cup.hilly.dataSource.model.mongo.result.TempTest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempTestSer {

    @Autowired
    TempTestDao tempTestDao;
    public void add(TempTest tempTest) {
        tempTestDao.save(tempTest);
    }
}
