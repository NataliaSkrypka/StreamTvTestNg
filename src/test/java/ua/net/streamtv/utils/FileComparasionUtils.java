package ua.net.streamtv.utils;


import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

/**
 * Created by nskrypka on 8/23/15.
 */
public class FileComparasionUtils {

    public static boolean compareImages(String expectedFile, String actualFile) throws IOException {
        BufferedImage bufileExp = ImageIO.read(new File(expectedFile));
        DataBuffer dafileExp = bufileExp.getData().getDataBuffer();
        int sizefileExp = dafileExp.getSize();

        BufferedImage bufileActual = ImageIO.read(new File(actualFile));
        DataBuffer dafileActual = bufileActual.getData().getDataBuffer();
        int sizefileActual = dafileActual.getSize();
        Boolean matchFlag = true;
        if (sizefileExp == sizefileActual) {
            for (int j = 0; j < sizefileExp; j++) {
                if (dafileExp.getElem(j) != dafileActual.getElem(j)) {
                    matchFlag = false;
                    break;
                }
            }
        } else
            matchFlag = false;
        return matchFlag;
    }

    public static boolean comparePdfs(String fileActual, String fileAbsolutePath) throws IOException {
        long fileSizeActual = FileUtils.checksumCRC32(new File(fileActual));
        long fileSizeExpected = FileUtils.checksumCRC32(new File(fileAbsolutePath));
        if (fileSizeActual == fileSizeExpected) {
            return true;
        } else {
            return false;
        }
    }
}
