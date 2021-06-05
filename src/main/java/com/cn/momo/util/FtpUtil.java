package com.cn.momo.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * dongwenmo 2021-05-16
 */
public class FtpUtil {
    private final static String ftphostaddr = "10.1.51.22";//服务器地址
    private final static String ftppath = "/ftp_app_0512_已完成/基线版本1.1/01.内部控制子系统/05.部署包/03.程序包/03.开源环境/01.程序包";//操作的服务器目录
    private final static String ftpname = "administrator";//服务器登录名
    private final static String ftppwd = "Dareway@2018";//登录密码

    private final static String localpath = getCurentContentPath() + "uploadfiles";
    private final static String fileSeparator = System.getProperty("file.separator");

    public static void downFile(String filename) {
        FTPClient ftp = initFtp();
        try {
            //4.指定要下载的目录 
            boolean flag = ftp.changeWorkingDirectory(new String(ftppath.getBytes("gbk"), "iso-8859-1"));// 转移到FTP服务器目录 
            if (!flag) {
                System.out.println("切换路径失败");
            }
            //5.遍历下载的目录 
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                //解决中文乱码问题，两次解码 
                byte[] bytes = ff.getName().getBytes("iso-8859-1");
                String fn = new String(bytes, "gbk");
                System.out.println(fn);
                if (fn.equals(filename)) {
                    System.out.println("下载");

                    //6.写操作，将其写入到本地文件中 
                    File localFile = new File("E:\\工作空间\\11.国家局中台\\06.核心区系统\\02.反编译源码\\demo\\" + fn);
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                    break;
                }
            }
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
            new Exception("从服务器下载文件过程中发生错误");
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }



    public static FTPClient initFtp() {
        int reply;
        FTPClient ftp = new FTPClient();
        try {
            // 1.连接服务器
            ftp.connect(ftphostaddr);
//            ftp.connect(ftphostaddr, 22);
            // 2.登录服务器 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(ftpname, ftppwd);
            // 3.判断登陆是否成功
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Exception("服务器连接失败");
        }
        return ftp;
    }

    public static String getCurentContentPath() {
        String path = "";
//        path = FtpUtil.class.getClassLoader().getResource("").toString();
//        path = path.replace("file:", "").substring(0, path.indexOf("WEB-INF")).replace("WEB-IN", "").replace("WEB-I", "");
        return path;
    }

    public static void main1(String[] args) {
        downFile("hsa-ics-clct-generic-local-1.1.0.jar");
    }


    public static void main(String[] args) throws Exception{
        String s = "楠岃瘉ssoticket澶辫触锛歋SOTicket鏃犳晥锛屾棤娉曡幏鍙栧瘑鐮侊紒";
        String str = new String(s.getBytes("gbk"), "utf-8");
        System.out.println(str);
    }
}