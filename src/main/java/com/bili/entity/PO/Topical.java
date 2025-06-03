package com.bili.entity.PO;

import lombok.Data;

@Data
public class Topical {
    private Integer tid;
    private Integer uid;
    private String topical;
    private Integer counts;
    private String intro;
    private Integer watch;
    //append
    private String avatar;
    private String name;
}
