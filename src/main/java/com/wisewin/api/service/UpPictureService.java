package com.wisewin.api.service;

import com.wisewin.api.util.OSSClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传与删除
 */
@Service
public class UpPictureService {

    /**
     * 上传图片至osss服务器
     * @param image
     * @return
     * @throws Exception
     */
    public  String upImage(MultipartFile image) throws Exception {

    OSSClientUtil ossClientUtil=new OSSClientUtil();
    //上传
    return ossClientUtil.uploadImg2Oss(image);

        //name:图片路径+图片名(图片名为生成的随机数)
    }

    /**
     * 删除图片
     * @param name
     */
    public void delImage(String name){
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        ossClientUtil.deleteFileInfo(name);
    }
}
