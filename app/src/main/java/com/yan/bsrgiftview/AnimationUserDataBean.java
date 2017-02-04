package com.yan.bsrgiftview;

import java.io.Serializable;

/**
 * Created by yan on 2016/12/16.
 */

public class AnimationUserDataBean implements Serializable {
    public String id;
    public String userName;
    public String imgPath;

    public AnimationUserDataBean(String id, String userName, String imgPath) {
        this.id = id;
        this.userName = userName;
        this.imgPath = imgPath;
    }
}
