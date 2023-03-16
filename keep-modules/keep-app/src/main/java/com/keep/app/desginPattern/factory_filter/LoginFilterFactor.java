package com.keep.app.desginPattern.factory_filter;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginFilterFactor {


    public static List<ILoginFilter> createLoginFilters(List<LoginFilterEnum> loginFilterEnums){
        List<ILoginFilter> loginFilters = null;
        if(CollUtil.isNotEmpty(loginFilterEnums)){
            loginFilters = loginFilterEnums.stream().map(x -> LoginFilterFactor.createLoginFilter(x)).collect(Collectors.toList());
        }

        return loginFilters;
    }

    private static ILoginFilter createLoginFilter(LoginFilterEnum x) {
        ILoginFilter filter = null;
        switch (x){
            case USER_PWD:
                filter = new UsernamePwdFilter();
                break;
        }
        return filter;
    }

}
