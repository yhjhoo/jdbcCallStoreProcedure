package com.example.jpademo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaDemoApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void call() {
        List<SqlParameter> parameter = new ArrayList<>();
        parameter.add(new SqlParameter(Types.INTEGER));
        parameter.add(new SqlOutParameter("msg", Types.FLOAT));

        Map<String, Object> out_value = jdbcTemplate.call(con -> {
            CallableStatement callableStatement = con.prepareCall("{call my_sqrt(?, ?)}");
            callableStatement.setInt(1, 4);
            callableStatement.registerOutParameter(2, Types.FLOAT);
            return callableStatement;
        }, parameter);

        System.out.println(out_value);

    }

    @Test
    public void simpleCall() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("my_sqrt")
                .declareParameters(
                        new SqlParameter("input_number", Types.INTEGER),
                        new SqlOutParameter("out_number", Types.FLOAT)
                );
        Map<String, Object> out_value = simpleJdbcCall.execute(new MapSqlParameterSource("input_number", 4));
        System.out.println(out_value);

        List<Map<String, Object>> maps = (List<Map<String, Object>>) out_value.get("#result-set-1");
        for (Map<String, Object> map : maps) {
            System.out.println(map.get("id"));
        }

    }
}
