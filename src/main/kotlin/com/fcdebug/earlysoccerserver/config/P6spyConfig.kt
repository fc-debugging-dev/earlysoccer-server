package com.fcdebug.earlysoccerserver.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat
import java.util.Date


@Configuration
class P6spyConfig {
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spyPrettySqlFormatter::class.java.name
    }
}

class P6spyPrettySqlFormatter : MessageFormattingStrategy {
    override fun formatMessage(
        connectionId: Int,
        now: String,
        elapsed: Long,
        category: String,
        prepared: String,
        sql: String,
        url: String
    ): String {
        var sql: String? = sql
        sql = formatSql(category, sql)
        val currentDate = Date()
        val format1 = SimpleDateFormat("yy.MM.dd HH:mm:ss")

        //return now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + P6Util.singleLine(prepared) + sql;
        return format1.format(currentDate) + " | " + "OperationTime : " + elapsed + "ms" + sql
    }

    private fun formatSql(category: String, sql: String?): String? {
        var sql = sql
        if (sql == null || sql.trim { it <= ' ' } == "") return sql

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.getName().equals(category)) {
            val tmpsql = sql.trim { it <= ' ' }.lowercase()
            sql = if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                FormatStyle.DDL.formatter.format(sql)
            } else {
                FormatStyle.BASIC.formatter.format(sql)
            }
            sql = "|\nHeFormatSql(P6Spy sql,Hibernate format):$sql"
        }
        return sql
    }
}