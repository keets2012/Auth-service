package com.blueskykong.auth.dao.typehandler;

import com.ecwid.consul.json.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ShortListTypeHandler implements TypeHandler<List<Short>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<Short> list, JdbcType jdbcType)
                                    throws SQLException {
        if (list == null) {
            list = Collections.emptyList();
        }
        Gson gson = GsonFactory.getGson();
        preparedStatement.setString(i, gson.toJson(list));
    }

    @Override
    public List<Short> getResult(ResultSet resultSet, String s) throws SQLException {
        String json = resultSet.getString(s);
        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>(0);
        }
        return transformJsonList(json);
    }

    @Override
    public List<Short> getResult(ResultSet resultSet, int i) throws SQLException {
        String json = resultSet.getString(i);
        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>(0);
        }
        return transformJsonList(json);
    }

    @Override
    public List<Short> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String json = callableStatement.getString(i);
        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>(0);
        }
        return transformJsonList(json);
    }

    private List<Short> transformJsonList(String json) {
        Gson gson = GsonFactory.getGson();
        Type listType = new TypeToken<List<Short>>(){}.getType();
        return gson.fromJson(json, listType);
    }
}
