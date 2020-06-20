package rpc.service;

public class HelloWorldImpl implements HelloWorld{
    public String  say(String arg){
        if(arg==null){
            return "远程调用，没有得到参数返回";
        }else {
            return "远程调用，返回结果"+arg;
        }

    }
}
