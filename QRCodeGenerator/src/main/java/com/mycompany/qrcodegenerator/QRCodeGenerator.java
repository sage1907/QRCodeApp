/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.qrcodegenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

//import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;

/**
 *
 * @author sagar
 */
public class QRCodeGenerator {
    
    public static void generateQRCode(String text, String filePath, String detailsFilePath) throws WriterException, IOException {
        int width = 300;
        int height = 300;
        String fileType = "png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, fileType, path);

        // Write details to file
        try (FileOutputStream fos = new FileOutputStream(detailsFilePath, true)) {
            fos.write(("QR Code for: " + text + "\n").getBytes());
        }
    }
    
    
    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\sagar\\OneDrive\\Desktop\\Output\\amazon.png";
            String detailsFilePath = "C:\\Users\\sagar\\OneDrive\\Desktop\\Output\\qrcode_details_amazon.txt";
            generateQRCode("https://www.amazon.com", filePath, detailsFilePath);
            System.out.println("QR Code generated successfully.");
        } catch (WriterException | IOException e) {
            System.err.println("Could not generate QR Code: " + e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }
}
