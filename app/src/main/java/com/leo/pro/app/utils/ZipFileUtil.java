package com.leo.pro.app.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;

/**
 * 创建人 LEO
 * 创建时间 2018/2/26 11:25
 */

public class ZipFileUtil {

    public interface UnzipListener{
        void onStartArchiver() ;
        void onProgressArchiver(float progress) ;
    }

    public static void unzip(String zipFile, String targetDir,UnzipListener listener){

        File file = new File(zipFile) ;
        try {
            ZipFile zip = new ZipFile(file) ;
            if(!zip.isValidZipFile()){
                System.out.print("Zip文件不合法");
            }else{
                File destDir = new File(targetDir) ;
                if(destDir.isDirectory() && !destDir.exists()){
                    destDir.mkdirs() ;
                }
                listener.onStartArchiver();
                FileHeader fh = null ;
                int total = zip.getFileHeaders().size() ;
                for(int i = 0; i < total ; i++){
                    fh = (FileHeader) zip.getFileHeaders().get(i);
                    zip.extractFile(fh,targetDir);
                    listener.onProgressArchiver((i+1)*1.0f/total*100);
                }
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
//    public static void unzip(String zipFile, String targetDir,UnzipListener listener) {
//
//        int BUFFER = 4096; //这里缓冲区我们使用4KB，
//        String strEntry; //保存每个zip的条目名称
//
//        try {
//            BufferedOutputStream dest = icon_0; //缓冲输出流
//            FileInputStream fis = new FileInputStream(zipFile);
//            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
//            ZipEntry entry; //每个zip条目的实例
//            while ((entry = zis.getNextEntry()) != icon_0) {
//                try {
//                    Log.i("Unzip: ","="+ entry);
//                    int count;
//                    byte data[] = new byte[BUFFER];
//                    strEntry = entry.getName();
//
//                    File entryFile = new File(targetDir + strEntry);
//                    File entryDir = new File(entryFile.getParent());
//                    if (!entryDir.exists()) {
//                        entryDir.mkdirs();
//                    }
//
//                    FileOutputStream fos = new FileOutputStream(entryFile);
//                    dest = new BufferedOutputStream(fos, BUFFER);
//                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
//                        dest.write(data, 0, count);
//                    }
//                    dest.flush();
//                    dest.close();
//                } catch (Exception ex) {
//                    listener.result(UNZIP_FAIL);
//                    ex.printStackTrace();
//                    return;
//                }
//            }
//            listener.result(UNZIP_SUCCESS);
//            zis.close();
//        } catch (Exception cwj) {
//            listener.result(UNZIP_FAIL);
//            cwj.printStackTrace();
//        }
//    }
}
