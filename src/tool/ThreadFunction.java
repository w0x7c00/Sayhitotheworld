package tool;

//此接口用于多线程的接口回调
//func传入的参数根据实现自己处理
public interface ThreadFunction {
    void func(Object data);
}