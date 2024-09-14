package com.bili.entity.outEntity;

import lombok.Data;

@Data
public class Addfavorite {
    private Integer uid;
    private Integer[] fids;
    private Integer[] type;  // == 1 增加  0 不变   -1 减少
    private Integer vid;
}
