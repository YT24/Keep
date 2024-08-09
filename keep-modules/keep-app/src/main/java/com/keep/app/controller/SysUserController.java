package com.keep.app.controller;

import com.keep.app.domain.dto.UserCreateDto;
import com.keep.app.mapper.UserMapper;
import com.keep.app.service.KeepUserService;
import com.keep.auth.fegin.client.UserCleint;
import com.keep.common.core.domain.entity.ResponseResult;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Api(tags = "用户中心")
@RequestMapping("/api/v1/user")
@RestController
public class SysUserController {

    @Autowired
    private KeepUserService sysUserService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCleint userCleint;

    @Autowired
    private Redisson redisson;



    @GlobalTransactional
    @ApiOperation("")
    @PostMapping("login")
    public ResponseResult seataTest(@RequestBody @Validated UserCreateDto createDto){
//        userMapper.insert(JSONObject.parseObject(JSONObject.toJSONString(createDto), User.class));
//        userCleint.create();
//        System.out.println(1/0);

        RLock aaa = redisson.getLock("AAA");
        aaa.lock(20, TimeUnit.SECONDS);

        ThreadLocal<UserCreateDto> threadLocal = new ThreadLocal();
        threadLocal.set(createDto);
        InheritableThreadLocal<UserCreateDto> inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set(createDto);


        return ResponseResult.success();
    }

    public static void main(String[] args) {
        //getMaxCountChart();
        //strContainStr();
        //System.out.println(minSubstring());
        System.out.println(Stream.of(4, 2, 3, 5, 1).max(Integer::max).get());


    }

    static void strContainStr(){
        String a = "hello world java";
        String b = "java";
        String regex = ".*java.*";
        boolean matches = a.matches(regex);
        System.out.println(matches);
    }


    /**
     * 找出字符串出现最大次数的字符
     */
    static void getMaxCountChart(){
        String  str  =    "aaabbbbcccccddddddqwertyuiop"  ;
        Map<Character,Integer>  countMap  =  new  HashMap<>();
        int  maxCount  =  0;
        Set<Character>  maxSet  =  new  HashSet<>();
        for  (int  i  =  0;  i  <  str.length();  i++)  {
            char  c  =  str.charAt(i);
            int  count  =  countMap.getOrDefault(c,  0)  +  1;
            countMap.put(c,  count);
            if  (count  >  maxCount)  {
                maxCount  =  count;
                maxSet.clear();
                maxSet.add(c);
            }  else  if  (count  ==  maxCount)  {
                maxSet.add(c);
            }
        }
        System.out.println(countMap);
        System.out.println(maxSet);
    }

    public static String minSubstring() {
        String target = "java";
        String str = "hello world java nice to meet you";
        //记录需要查找的字母以及它们出现的次数
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        //使用两个指针来表示滑动窗口
        int start = 0, end = 0;
        //当前滑动窗口中包含的需要查找的字母的数量
        int count = target.length();
        //记录结果
        int minLen = Integer.MAX_VALUE;
        String res = "";

        while (end < str.length()) {
            char c1 = str.charAt(end);
            if (map.containsKey(c1)) {
                map.put(c1, map.get(c1) - 1);
                if (map.get(c1) >= 0) {
                    count--;
                }
            }
            end++;

            while (count == 0) {
                //记录满足条件的最小子字符串
                if (end - start < minLen) {
                    minLen = end - start;
                    res = str.substring(start, end);
                }
                char c2 = str.charAt(start);
                if (map.containsKey(c2)) {
                    map.put(c2, map.get(c2) + 1);
                    if (map.get(c2) > 0) {
                        count++;
                    }
                }
                start++;
            }
        }

        return res;
    }
}
