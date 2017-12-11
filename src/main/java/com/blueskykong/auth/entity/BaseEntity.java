package com.blueskykong.auth.entity;

import com.blueskykong.auth.utils.TimestampAdapter;
import lombok.Data;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by keets on 2016/12/5.
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8388417013613884411L;

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Timestamp createTime;

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Timestamp updateTime;

    private int createBy;

    private int updateBy;
}
