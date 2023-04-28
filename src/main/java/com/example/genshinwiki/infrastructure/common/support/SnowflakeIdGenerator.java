package com.example.genshinwiki.infrastructure.common.support;

import cn.hutool.core.lang.Snowflake;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * @author zxd
 * @date 2022/6/2 16:14
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {
    public final static String TYPE = "com.example.genshinwiki.infrastructure.common.support.SnowflakeIdGenerator";

    public final static Snowflake SNOWFLAKE = new Snowflake(
            Date.from(Instant.ofEpochMilli(1570606636805L)), 0, 0, false
    );

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return SNOWFLAKE.nextIdStr();
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 20; i++) {
//            System.out.println(SNOWFLAKE.nextIdStr());
//        }
//    }
}
