package com.dfj.baiduclubchat.model;

/**
 * Created by DC on 2015/12/4.
 */
import java.util.HashMap;

public class ManageClientConServer {
    private static HashMap hm=new HashMap<Integer,ClientConServerThread>();
    //�Ѵ����õ�ClientConServerThread���뵽hm
    public static void addClientConServerThread(int account,ClientConServerThread ccst){
        hm.put(account, ccst);
    }

    //����ͨ��accountȡ�ø��߳�
    public static ClientConServerThread getClientConServerThread(int i){
        return (ClientConServerThread)hm.get(i);
    }
}
