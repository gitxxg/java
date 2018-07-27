package cn.ghl.web.entity;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
@Document(indexName = "stock", type = "mainTarget")
public class MainTarget implements Serializable {

    @Id
    private String name;

    private String date;

    private String jbmgsy;

    private String kfmgsy;

    private String xsmgsy;

    private String mgjzc;

    private String mggjj;

    private String mgwfply;

    private String mgjyxjl;

    private String yyzsr;

    private String mlr;

    private String gsjlr;

    private String kfjlr;

    private String yyzsrtbzz;

    private String gsjlrtbzz;

    private String kfjlrtbzz;

    private String yyzsrgdhbzz;

    private String gsjlrgdhbzz;

    private String kfjlrgdhbzz;

    private String jqjzcsyl;

    private String tbjzcsyl;

    private String tbzzcsyl;

    private String mll;

    private String jll;

    private String sjsl;

    private String yskyysr;

    private String xsxjlyysr;

    private String jyxjlyysr;

    private String zzczzy;

    private String yszkzzts;

    private String chzzts;

    private String zcfzl;

    private String ldzczfz;

    private String ldbl;

    private String sdbl;

    public MainTarget() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJbmgsy() {
        return jbmgsy;
    }

    public void setJbmgsy(String jbmgsy) {
        this.jbmgsy = jbmgsy;
    }

    public String getKfmgsy() {
        return kfmgsy;
    }

    public void setKfmgsy(String kfmgsy) {
        this.kfmgsy = kfmgsy;
    }

    public String getXsmgsy() {
        return xsmgsy;
    }

    public void setXsmgsy(String xsmgsy) {
        this.xsmgsy = xsmgsy;
    }

    public String getMgjzc() {
        return mgjzc;
    }

    public void setMgjzc(String mgjzc) {
        this.mgjzc = mgjzc;
    }

    public String getMggjj() {
        return mggjj;
    }

    public void setMggjj(String mggjj) {
        this.mggjj = mggjj;
    }

    public String getMgwfply() {
        return mgwfply;
    }

    public void setMgwfply(String mgwfply) {
        this.mgwfply = mgwfply;
    }

    public String getMgjyxjl() {
        return mgjyxjl;
    }

    public void setMgjyxjl(String mgjyxjl) {
        this.mgjyxjl = mgjyxjl;
    }

    public String getYyzsr() {
        return yyzsr;
    }

    public void setYyzsr(String yyzsr) {
        this.yyzsr = yyzsr;
    }

    public String getMlr() {
        return mlr;
    }

    public void setMlr(String mlr) {
        this.mlr = mlr;
    }

    public String getGsjlr() {
        return gsjlr;
    }

    public void setGsjlr(String gsjlr) {
        this.gsjlr = gsjlr;
    }

    public String getKfjlr() {
        return kfjlr;
    }

    public void setKfjlr(String kfjlr) {
        this.kfjlr = kfjlr;
    }

    public String getYyzsrtbzz() {
        return yyzsrtbzz;
    }

    public void setYyzsrtbzz(String yyzsrtbzz) {
        this.yyzsrtbzz = yyzsrtbzz;
    }

    public String getGsjlrtbzz() {
        return gsjlrtbzz;
    }

    public void setGsjlrtbzz(String gsjlrtbzz) {
        this.gsjlrtbzz = gsjlrtbzz;
    }

    public String getKfjlrtbzz() {
        return kfjlrtbzz;
    }

    public void setKfjlrtbzz(String kfjlrtbzz) {
        this.kfjlrtbzz = kfjlrtbzz;
    }

    public String getYyzsrgdhbzz() {
        return yyzsrgdhbzz;
    }

    public void setYyzsrgdhbzz(String yyzsrgdhbzz) {
        this.yyzsrgdhbzz = yyzsrgdhbzz;
    }

    public String getGsjlrgdhbzz() {
        return gsjlrgdhbzz;
    }

    public void setGsjlrgdhbzz(String gsjlrgdhbzz) {
        this.gsjlrgdhbzz = gsjlrgdhbzz;
    }

    public String getKfjlrgdhbzz() {
        return kfjlrgdhbzz;
    }

    public void setKfjlrgdhbzz(String kfjlrgdhbzz) {
        this.kfjlrgdhbzz = kfjlrgdhbzz;
    }

    public String getJqjzcsyl() {
        return jqjzcsyl;
    }

    public void setJqjzcsyl(String jqjzcsyl) {
        this.jqjzcsyl = jqjzcsyl;
    }

    public String getTbjzcsyl() {
        return tbjzcsyl;
    }

    public void setTbjzcsyl(String tbjzcsyl) {
        this.tbjzcsyl = tbjzcsyl;
    }

    public String getTbzzcsyl() {
        return tbzzcsyl;
    }

    public void setTbzzcsyl(String tbzzcsyl) {
        this.tbzzcsyl = tbzzcsyl;
    }

    public String getMll() {
        return mll;
    }

    public void setMll(String mll) {
        this.mll = mll;
    }

    public String getJll() {
        return jll;
    }

    public void setJll(String jll) {
        this.jll = jll;
    }

    public String getSjsl() {
        return sjsl;
    }

    public void setSjsl(String sjsl) {
        this.sjsl = sjsl;
    }

    public String getYskyysr() {
        return yskyysr;
    }

    public void setYskyysr(String yskyysr) {
        this.yskyysr = yskyysr;
    }

    public String getXsxjlyysr() {
        return xsxjlyysr;
    }

    public void setXsxjlyysr(String xsxjlyysr) {
        this.xsxjlyysr = xsxjlyysr;
    }

    public String getJyxjlyysr() {
        return jyxjlyysr;
    }

    public void setJyxjlyysr(String jyxjlyysr) {
        this.jyxjlyysr = jyxjlyysr;
    }

    public String getZzczzy() {
        return zzczzy;
    }

    public void setZzczzy(String zzczzy) {
        this.zzczzy = zzczzy;
    }

    public String getYszkzzts() {
        return yszkzzts;
    }

    public void setYszkzzts(String yszkzzts) {
        this.yszkzzts = yszkzzts;
    }

    public String getChzzts() {
        return chzzts;
    }

    public void setChzzts(String chzzts) {
        this.chzzts = chzzts;
    }

    public String getZcfzl() {
        return zcfzl;
    }

    public void setZcfzl(String zcfzl) {
        this.zcfzl = zcfzl;
    }

    public String getLdzczfz() {
        return ldzczfz;
    }

    public void setLdzczfz(String ldzczfz) {
        this.ldzczfz = ldzczfz;
    }

    public String getLdbl() {
        return ldbl;
    }

    public void setLdbl(String ldbl) {
        this.ldbl = ldbl;
    }

    public String getSdbl() {
        return sdbl;
    }

    public void setSdbl(String sdbl) {
        this.sdbl = sdbl;
    }
}
