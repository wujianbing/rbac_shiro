package com.jack.rbac_shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

/**
 * 散列加密
 */
public class MD5Test {

    @Test
    public void md5test(){

        String name = "md5";
        String pwd = "123456";

        Object result = new SimpleHash(name,pwd,null,2);

        System.out.println(result);
    }
}
