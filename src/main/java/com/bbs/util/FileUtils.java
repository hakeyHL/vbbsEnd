package com.bbs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by lihongde on 2016/7/13.
 */
public class FileUtils {

    public static int DEFAULT_THUMBNAIL_WIDTH = 265;
    public static int DEFAULT_THUMBNAIL_HEIGHT = 265;

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static List<String> saveFilesToDisk(HttpServletRequest request, String savePath) {
        return saveFilesToDisk(request, savePath, false);
    }

    public static List<String> saveFilesToDisk(HttpServletRequest request,String savePath, boolean createThumbnail) {
        List<String> fileNameList = new ArrayList<String>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                String fileName = iter.next();
                List<MultipartFile> fileList = multiRequest.getFiles(fileName);
                for (MultipartFile file : fileList) {
                    if (file != null && file.getSize() > 0) {
                        String orginalFileName = file.getOriginalFilename();
                        String extName = TextUtils.getFileExtName(orginalFileName, "png");
                        String mainPart = UUID.randomUUID().toString();
                        String newFileName = mainPart + "." + extName;
                        String newPath = savePath + newFileName;
                        File localFile = new File(newPath);
                        if(!localFile.exists()){
                            localFile.mkdirs();
                        }
                        try {
                            file.transferTo(localFile);
                        } catch (IllegalStateException e) {
                            logger.error("上传图片出错 ");
                            e.printStackTrace();
                        } catch (IOException e) {
                            logger.error("上传图片出错");
                            e.printStackTrace();
                        }
                        fileNameList.add(newFileName);
                        logger.info("upload file done. path:" + newPath);

                        // createThumbnail
                        if (createThumbnail) {
                            String thumbnailImagePath = savePath + mainPart + "_" + DEFAULT_THUMBNAIL_WIDTH + "x" + DEFAULT_THUMBNAIL_HEIGHT + "." + extName;
                            thumbnailImage(localFile, DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT, thumbnailImagePath, false);
                            logger.info("create thumnail file done. path:" + thumbnailImagePath);
                        }
                    }
                }

            }
        }

        return fileNameList;

    }

    /**
     * 复制单个文件
     *
     * @param srcFileName
     *            待复制的文件名
     * @param destFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        File srcFile = new File(srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            return false;
        } else if (!srcFile.isFile()) {
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件
     * @param destFileName
     * @return
     */
    public static void createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.isFile() && file.exists()) {
            file.delete();
            logger.info("删除已经存在的文件");
        }
        if (destFileName.endsWith(File.separator)) {
            logger.info("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
        }
        if(!file.getParentFile().exists()) {
            if(!file.getParentFile().mkdirs()) {
                logger.info("创建目标文件所在目录失败！");
            }
        }
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
            } else {
                logger.info("创建单个文件" + destFileName + "失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("创建单个文件" + destFileName + "失败！" + e.getMessage());
        }
    }

    public static String generatePictureName(MultipartFile file) {
        String originName = file.getOriginalFilename();
        String suffix = originName.substring(originName.lastIndexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + suffix;
    }

    public static void transferFile(String path, String fileName, MultipartFile file) {
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("保存文件失败");
            e.printStackTrace();
        }
    }

    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param imgFile      原图片路径
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param newPath      生成缩略图的路径
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static void thumbnailImage(File imgFile, int w, int h, String newPath, boolean force){
        if(imgFile.exists()){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if(imgFile.getName().indexOf(".") > -1) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0){
                    logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
                logger.debug("target image's size, width:{}, height:{}.",w,h);
                Image img = ImageIO.read(imgFile);
                if(!force){
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                            logger.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                            logger.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                ImageIO.write(bi, suffix, new File(newPath));
            } catch (IOException e) {
                logger.error("generate thumbnail image failed.",e);
            }
        }else{
            logger.warn("the image is not exist.");
        }
    }
}
