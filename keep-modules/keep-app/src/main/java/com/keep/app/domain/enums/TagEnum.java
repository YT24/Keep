package com.keep.app.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public enum TagEnum {
    TABLE("label", "label标签的属性", IPropsType.LabelProps.class),
    LABEL("table", "tablel标签的属性", IPropsType.TableProps.class);

    public final String code;
    public final String desc;
    public final Class<? extends TagEnum.IPropsType> propsEnum;
    public IPropsType[] typeValues;

    TagEnum(String code, String desc, Class<? extends TagEnum.IPropsType> propsEnum) {
        this.propsEnum = propsEnum;
        this.code = code;
        this.desc = desc;
        this.typeValues = propsEnum.getEnumConstants();
    }

    public interface IPropsType {
        @Getter
        @AllArgsConstructor
        enum TableProps implements IPropsType {
            NAME("name_tabel"),
            AGE("age");
            public final String code;
        }

        @Getter
        @AllArgsConstructor
        enum LabelProps implements IPropsType {
            NAME("name_label");
            public final String code;
        }

        String getCode();
    }

    public static List<String> getProps(String tag) {
        List<String> collect = new ArrayList<>();
        for (TagEnum e : TagEnum.values()) {
            if (Objects.equals(tag, e.code)) {
                collect = Arrays.stream(e.typeValues).map(IPropsType::getCode).collect(Collectors.toList());
            }
        }
        return collect;
    }

    public static void main(String[] args) {
        List<String> fields = TagEnum.TABLE.getProps("table");
        System.out.println(fields);

    }
}
