package kr.co.kyobo.vora.config;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class MysqlUnicodeDialect extends MySQL57Dialect {
    public MysqlUnicodeDialect() {
        registerColumnType(Types.CLOB,StandardBasicTypes.TEXT.getName());
    }
}
