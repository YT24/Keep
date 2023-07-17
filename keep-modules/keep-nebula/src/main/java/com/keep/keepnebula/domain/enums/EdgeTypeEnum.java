package com.keep.keepnebula.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum EdgeTypeEnum {

    JOIN("join", "表的join关系", IEdgePropsType.JoinProps.class),
    LINKS_TO("linksTo", "通用标识点之间的单项关系的类型 用作兜底的Edge Type", IEdgePropsType.LinksToProps.class),
    HASLAB("hasLab", "tbl 到label的关系", null),
    BELONG_TO("belongsTo", "label 到 tbl", null);

    public final String code;
    public final String desc;
    public final Class<? extends IEdgePropsType> propsEnum;

    public IEdgePropsType[] typeValues;

    EdgeTypeEnum(String code, String desc, Class<? extends IEdgePropsType> propsEnum) {
        this.propsEnum = propsEnum;
        this.code = code;
        this.desc = desc;
        this.typeValues = propsEnum != null ? propsEnum.getEnumConstants() : null;
    }

    public interface IEdgePropsType {
        @Getter
        @AllArgsConstructor
        enum JoinProps implements IEdgePropsType {
            SRC_COL("src_col"),
            DEST_COL("dest_col"),
            JOIN_TYPE("join_type"),
            SOURCE("source");
            public final String code;
        }

        @Getter
        @AllArgsConstructor
        enum LinksToProps implements IEdgePropsType {
            TYPE("type"),
            RELATION("relation");
            public final String code;
        }

        String getCode();
    }

    public static List<String> getProps(String tag) {
        List<String> collect = new ArrayList<>();
        for (EdgeTypeEnum e : EdgeTypeEnum.values()) {
            if (Objects.equals(tag, e.code)) {
                collect = Arrays.stream(e.typeValues).map(IEdgePropsType::getCode).collect(Collectors.toList());
            }
        }
        return collect;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        List<String> props = EdgeTypeEnum.getProps(EdgeTypeEnum.JOIN.code);
        System.out.println(props);
    }

}
