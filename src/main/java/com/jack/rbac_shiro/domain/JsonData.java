package com.jack.rbac_shiro.domain;

import java.io.Serializable;

/**
 * 数据响应类
 */
public class JsonData implements Serializable {


public static  final long serialVersionUID = 1L;
    private Integer code;
    private Object data;
    private String message;

    public JsonData(Integer code,Object data,String message){
        this.code = code; //状态码
        this.data = data; //数据
        this.message = message; //返回信息
    }
    public static JsonData buildSuccess(Integer code,String message){return new JsonData(code,null,message);}
    public static JsonData buildSuccess(){return new JsonData(0,null,null);}
    public static JsonData buildSuccess(Object data,String message){return new JsonData(0,data,message);}
    public static JsonData buildSuccess(Object data){return new JsonData(0,data,null);}
    public static JsonData buildSuccess(Integer code,Object data,String message){return new JsonData(code,data,message);}
    public static JsonData buildError(String message){return new JsonData(-1,null,message);}
    public static JsonData buildError(Integer code,String message){return new JsonData(code,null,message);}

    public Integer getCode() { return code; }

    public void setCode(Integer code) {this.code = code;}

    public Object getData() { return data; }

    public void setData(Object data) {this.data = data;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    @Override
    public String toString() {
        return "JsonData [code="+code+",data="+data+",message="+message+"]";
    }
}
