package cn.edu.cup.hilly;

//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.edu.cup.hilly.dataSource.mapper")
public class HillyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HillyApplication.class, args);
    }

}
