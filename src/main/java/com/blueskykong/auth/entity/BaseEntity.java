package com.blueskykong.auth.entity;

import com.blueskykong.auth.utils.TimestampAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * Created by keets on 2016/12/5.
 */

public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8388417013613884411L;

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Timestamp createTime;

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Timestamp updateTime;

    private int createBy;

    private int updateBy;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }
}
