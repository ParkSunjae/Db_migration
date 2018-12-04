package kr.co.kyobo.vora.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.descriptor.sql.*;

import java.sql.Types;

@Slf4j
public class SQLServerUnicodeDialect extends SQLServerDialect {
    public SQLServerUnicodeDialect() {
        super();
    }
    @Override
    public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        if (Types.CLOB == sqlTypeDescriptor.getSqlType()) {
            return LongVarcharTypeDescriptor.INSTANCE;
        }
        return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
    }
}
