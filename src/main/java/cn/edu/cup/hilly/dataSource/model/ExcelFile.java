package cn.edu.cup.hilly.dataSource.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CodeChap
 * @date 2021-05-21 16:39
 */
@Document
public class ExcelFile {

    @Id
    private String _id;

    private String hillyId;
    private double[][] lz;
    private Integer inum;

    public Integer getInum() {
        return inum;
    }

    public void setInum(Integer inum) {
        this.inum = inum;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHillyId() {
        return hillyId;
    }

    public void setHillyId(String hillyId) {
        this.hillyId = hillyId;
    }

    public double[][] getLz() {
        return lz;
    }

    public void setLz(double[][] lz) {
        this.lz = lz;
    }
}
