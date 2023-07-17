package org.nebula.contrib.ngbatis.binding.beetl.functions;

// Copyright (c) 2022 All project authors. All rights reserved.
//
// This source code is licensed under Apache 2.0 License.

import org.apache.commons.text.StringEscapeUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * 对传递给数据库的值进行不同类型的格式化
 *
 * @author yeweicheng
 * @since 2022-08-24 15:52
 * <br>Now is history!
 */
public class ValueFmtFn extends AbstractFunction<Object, Boolean, Boolean, Void, Void, Void> {

    private static boolean escape = true;

    public static void setEscape(boolean escape) {
        ValueFmtFn.escape = escape;
    }

    @Override
    public Object call(Object value, Boolean ifStringLike, Boolean escape) {
        ifStringLike = ifStringLike != null && ifStringLike;
        escape = escape != null ? escape : ValueFmtFn.escape;
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return ifStringLike
                    ? "'.*" + value + ".*'"
                    : "'" + (escape ? StringEscapeUtils.escapeJava((String) value) : value) + "'";
        }

        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        }

        if (value instanceof Duration) {
            return String.format(
                    "duration({seconds: %d})",
                    ((Duration) value).getSeconds()
            );
        }

        if (value instanceof Date) {
            Class<?> objClass = value.getClass();
            if (objClass == Timestamp.class) {
                // 数据库时间戳的单位是秒
                return String.format("%s(%d)", "timestamp", (((Timestamp) value).getTime() / 1000));
            }

            String timePattern = objClass == java.util.Date.class ? "yyyy-MM-dd'T'HH:mm:ss.sss"
                    : objClass == java.sql.Date.class ? "yyyy-MM-dd"
                    : objClass == java.sql.Time.class ? "HH:mm:ss.sss"
                    : "yyyy-MM-dd'T'HH:mm:ss.sss";
            SimpleDateFormat sdf = new SimpleDateFormat(timePattern);

            String fn = "datetime";
            fn = objClass == java.util.Date.class ? "datetime"
                    : objClass == java.sql.Date.class ? "date"
                    : objClass == java.sql.Time.class ? "time"
                    : fn;
            return String.format("%s('%s')", fn, sdf.format(value));
        }
        return value;
    }
}
