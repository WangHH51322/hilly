package cn.edu.cup.hilly;

import cn.edu.cup.hilly.calculate.hilly.large.Pump;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeChap
 * @date 2021-05-26 22:02
 */
public class Test {
    public void main(String[] args) {
        double[] doubles = new double[5];
        List<test> tests = new ArrayList<>();
        for (int i = 0; i < doubles.length; i++) {
            test test = new test();
            test.setStart(i*0.5);
            test.setEnd((i+1)*0.5);
            test.setType(doubles[i]);
            tests.add(test);
        }

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i).toString());
        }
    }

    class test {
        double start;
        double end;
        double type;

        public double getStart() {
            return start;
        }

        public void setStart(double start) {
            this.start = start;
        }

        public double getEnd() {
            return end;
        }

        public void setEnd(double end) {
            this.end = end;
        }

        public double getType() {
            return type;
        }

        public void setType(double type) {
            this.type = type;
        }
    }
}
