package com.example.readwrite.bean;
 
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@Data
@TableName("goods_1")
public class Goods {

    @Setter
    private Long gid;
    @Setter
    private String gname;
    @Setter
    private Long userId;
    @Setter
    private String gstatus;

}