package com.wisewin.api.entity.dto;

import com.wisewin.api.entity.bo.common.constants.Result;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.util.env.Env;

/**
 * Created by sunshibo on 2016/3/16.
 */
public class ResultDTOBuilder {

    public ResultDTOBuilder() {
    }

    public static <T> Result success(T data) {
        Env env = new Env();
        if(data==null || data.equals("")){
          return   getInstance("0000000",StringUtils.clearNull(env.getProperty("0000000")),new Object());
        }else{
            return   getInstance("0000000",StringUtils.clearNull(env.getProperty("0000000")),data);
        }
    }

    public static <T> Result success(String msg) {
            return   getInstance("0000000",msg,new Object());
    }

    public static Result failure(String errCode) {
        Env env = new Env();
        return   new Result(new Object(),errCode,StringUtils.clearNull(env.getProperty(errCode)));
    }



/*
    public static <T> ResultDTO<T> success(T data) {
        ResultDTO<T> instance = getInstance("", "", true, data);
        return instance;
    }
    public static <T> ResultDTO<T> failure(T data) {
        ResultDTO<T> instance = getInstance("", "", false, data);
        return instance;
    }

    public static ResultDTO failure(String errCode, String errMsg) {
        ResultDTO<String> instance = getInstance(errCode, errMsg, false, "");
        return instance;
    }

    public static ResultDTO failure(String errCode, String errMsg, String data) {
        ResultDTO<String> instance = getInstance(errCode, errMsg, false, data);
        return instance;
    }

    public static ResultDTO failure(String errCode) {
        Env env = new Env();
        ResultDTO<String> instance = getInstance(errCode, StringUtils.clearNull(env.getProperty(errCode)), false, "");
        return instance;
    }


    public static <T> ResultDTO<T> failures(String errCode, T date) {
        Env env = new Env();
        ResultDTO<T> instance = getInstance(errCode, StringUtils.clearNull(env.getProperty(errCode)), false, date);
        return instance;
    }

    public static <T> ResultDTO<T> getInstance(String errCode, String errMsg, boolean success, T data) {
        ResultDTO<T> resultDTO = new ResultDTO<T>();
        resultDTO.setData(data);
        resultDTO.setErrCode(errCode);
        resultDTO.setErrMsg(errMsg);
        resultDTO.setSuccess(success);
        return resultDTO;
    }*/

    public static <T> Result getInstance(String errCode,String msg, T data) {
        Result<T> result = new Result<T>();
        result.setData(data);
        result.setCode(errCode);
        result.setMsg(msg);
        return result;
    }

    public static void main(String[]  args){
       // StringUtils.clearNull(env.getProperty("01"))
    }
}
