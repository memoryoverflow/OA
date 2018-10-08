package com.yj.oa.common.utils.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public  class Encryption {
    public  static  Object getMD5(String pwd,String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //密码
        Object credentials = pwd;
        //使用ByteSource.Util.bytes()计算盐值
        Object Salt = ByteSource.Util.bytes(salt);
        //加密次数
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, Salt, hashIterations);
        return result;
    }

    public static void main(String []args){
        //加密方式
        String hashAlgorithmName = "MD5";
        //密码
        Object credentials = "admin";
        //使用ByteSource.Util.bytes()计算盐值
        ByteSource salt = ByteSource.Util.bytes("1111111111");
        //加密次数
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }

}
