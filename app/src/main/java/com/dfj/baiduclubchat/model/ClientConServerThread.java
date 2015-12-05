package com.dfj.baiduclubchat.model;

/**
 * Created by DC on 2015/12/4.
 */
/**
 * �ͻ��˺ͷ������˱���ͨ�ŵ��߳�
 * ���ϵض�ȡ����������������
 */;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.dfj.baiduclubchat.common.ClubMessage;
import com.dfj.baiduclubchat.common.ClubMessageType;

public class ClientConServerThread extends Thread {
    private Context context;
    private  Socket s;
    public Socket getS() {return s;}
    public ClientConServerThread(Context context,Socket s){
        this.context=context;
        this.s=s;
    }

    @Override
    public void run() {
        while(true){
            ObjectInputStream ois = null;
            ClubMessage m;
            try {
                ois = new ObjectInputStream(s.getInputStream());
                m=(ClubMessage) ois.readObject();
                if(m.getType().equals(ClubMessageType.COM_MES)
                        || m.getType().equals(ClubMessageType.GROUP_MES)){//�����������Ϣ
                    //�Ѵӷ�������õ���Ϣͨ���㲥����
                    Intent intent = new Intent("org.yhn.yq.mes");
                    String[] message=new String[]{
                            m.getGaccount()+"",
                            m.getGroupNick(),
                            m.getSender()+"",
                            m.getSenderNick(),
                            m.getSenderAvatar()+"",
                            m.getContent(),
                            m.getSendTime(),
                            m.getType()};
                    Log.i("--", message.toString());
                    intent.putExtra("message", message);
                    context.sendBroadcast(intent);
                }else if(m.getType().equals(ClubMessageType.RET_ONLINE_FRIENDS)){//����Ǻ����б�
                    //���º��ѣ�Ⱥ
                    String s[] = m.getContent().split(",");
                    //Log.i("", "--"+s[0]);
                    //Log.i("", "--"+s[1]);
                    //BuddyActivity.buddyStr=s[0];
                    //GroupActivity.groupStr=s[1];
                }
                if(m.getType().equals(ClubMessageType.SUCCESS)){
                    Toast.makeText(context, "�����ɹ���", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //e.printStackTrace();
                try {
                    if(s!=null){
                        s.close();
                    }
                } catch (IOException e1) {
                    //e1.printStackTrace();
                }
            }
        }
    }

}
