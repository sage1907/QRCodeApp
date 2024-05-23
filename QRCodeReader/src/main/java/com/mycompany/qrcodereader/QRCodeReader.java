/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.qrcodereader;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author sagar
 */
public class QRCodeReader {
    
    public static String readQRCode(String filePath) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(new File(filePath));
        BufferedImageLuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
        
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
    
    public static void main(String[] args) {
        String filePath = "C:\\Users\\sagar\\OneDrive\\Desktop\\Output\\amazon.png";
        String detailsFilePath = "C:\\Users\\sagar\\OneDrive\\Desktop\\Output\\qrcode_read_details2.txt";
        try {
            String decodedText = readQRCode(filePath);
            System.out.println("Decoded text: " + decodedText);
            
            // Write details to file
            try (FileOutputStream fos = new FileOutputStream(detailsFilePath, true)) {
                fos.write(("Decoded QR Code content: " + decodedText + "\n").getBytes());
            }
        } catch (IOException | NotFoundException e) {
            System.err.println("Could not read QR Code: " + e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }
}
