package com.payable.util.mysqldialect;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.IntegerType;

public class CustomMySQLDialect extends MySQLDialect {
	public CustomMySQLDialect() {
        super();
        registerFunction("timestampdiff_minute", new SQLFunctionTemplate(IntegerType.INSTANCE,
                "TIMESTAMPDIFF(MINUTE, ?1, ?2)"));
    }
}
