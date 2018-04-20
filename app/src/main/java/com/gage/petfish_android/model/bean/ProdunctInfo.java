package com.gage.petfish_android.model.bean;

import java.io.Serializable;

/**
 * Created by zhanglonglong on 2017/11/24.
 */

public class ProdunctInfo implements Serializable {
    private String sarverFileName;
    private String pName;
    private String seqId;

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getSarverFileName() {
        return sarverFileName;
    }

    public void setSarverFileName(String sarverFileName) {
        this.sarverFileName = sarverFileName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
