package cn.edu.cup.hilly.dataSource.model.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WiselyMessage {
    private String name;
    private String routingKey;
    private String msg;
    private Object object;
    private Map<Integer, double[]> dpl;
    private double[] uLocation;
}

