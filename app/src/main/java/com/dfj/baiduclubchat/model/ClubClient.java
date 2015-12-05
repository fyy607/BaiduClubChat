package com.dfj.baiduclubchat.model;

import android.content.Context;

import com.dfj.baiduclubchat.MainActivity;
import com.dfj.baiduclubchat.common.ClubMessage;
import com.dfj.baiduclubchat.common.ClubMessageType;
import com.dfj.baiduclubchat.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by DC on 2015/12/4.
 */
public class ClubClient {
    private Context context;
    public Socket s;
    public ClubClient(Context context){
        this.context = context;
    }
    public boolean sendLoginInfo(Object obj){
        boolean b=false;
        try {
            s=new Socket();
            try{
                s.connect(new InetSocketAddress("192.168.1.101",9999),2000);
            }catch(SocketTimeoutException e){
                //���ӷ�������ʱ
                return false;
            }
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            ClubMessage ms=(ClubMessage)ois.readObject();
            if(ms.getType().equals(ClubMessageType.SUCCESS)){
                //������Ϣ
                MainActivity.myInfo=ms.getContent();
                //����һ�����˺źͷ������������ӵ��߳�
                ClientConServerThread ccst=new ClientConServerThread(context,s);
                //������ͨ���߳�
                ccst.start();
                //���뵽��������
                ManageClientConServer.addClientConServerThread(((User)obj).getAccount(), ccst);
                b=true;
            }else if(ms.getType().equals(ClubMessageType.FAIL)){
                b=false;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public int sendRegisterInfo(Object obj){
        int b=-1;
        try {
            s=new Socket();
            try{
                s.connect(new InetSocketAddress("192.168.1.101",9999),2000);
            }catch(SocketTimeoutException e){
                //���ӷ�������ʱ
                return -1;
            }
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            ClubMessage ms=(ClubMessage)ois.readObject();
            if(ms.getType().equals(ClubMessageType.SUCCESS)){
                b=ms.getReceiver();
            }else if(ms.getType().equals(ClubMessageType.FAIL)){
                b=-1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }



}
