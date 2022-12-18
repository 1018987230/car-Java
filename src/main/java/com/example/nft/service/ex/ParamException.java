package com.example.nft.service.ex;

/**
 * @Author wangyixiong
 * @Date 2022/11/19 下午5:50
 * @PackageName:com.example.nft.service.ex
 * @ClassName: ParamException
 * @Description: TODO
 * @Version 1.0
 */
public class ParamException extends  ServiceException{

    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    protected ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
