package tool;
//这是多线程回调的实现类
//根据传入的参数信息实现多线程
public class ThreadBasic implements Runnable{
    private String threadName;
    private ThreadFunction funcObject;
    private Object data;

    public ThreadBasic(ThreadFunction funcObject, Object data, String threadName){
        this.funcObject = funcObject;
        this.threadName = threadName;
        this.data = data;
    }

    @Override
    public void run() {
        funcObject.func(data);
    }

    public void start () {
        new Thread (this, threadName).start();
    }
}
