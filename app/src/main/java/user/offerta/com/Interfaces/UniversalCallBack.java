package user.offerta.com.Interfaces;


public interface UniversalCallBack {

    void onResponse(Object result);
    void onFailure(Object result);
    void onFinish();
    void OnError(String message);

}
