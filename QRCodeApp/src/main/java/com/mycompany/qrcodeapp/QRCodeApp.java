/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.qrcodeapp;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

/**
 *
 * @author sagar
 */
public class QRCodeApp {
    
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

    public static String readQRCode(String filePath) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(new File(filePath));
        BufferedImageLuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));

        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choose an option:");
            System.out.println("1: Generate QR Code");
            System.out.println("2: Read QR Code");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the text or URL to encode in the QR Code:");
                    String text = scanner.nextLine();
                    System.out.println("Enter the path to save the QR Code image (e.g., output/qrcode.png):");
                    String generateFilePath = scanner.nextLine();
                    System.out.println("Enter the path to save the details file (e.g., output/qrcode_details.txt):");
                    String generateDetailsFilePath = scanner.nextLine();
                    
                    try {
                        generateQRCode(text, generateFilePath, generateDetailsFilePath);
                        System.out.println("QR Code generated successfully.");
                    } catch (WriterException | IOException e) {
                        System.err.println("Could not generate QR Code: " + e.getMessage());
                    }
                }
                    
                case 2 -> {
                    System.out.println("Enter the path of the QR Code image to read (e.g., output/qrcode.png):");
                    String readFilePath = scanner.nextLine();
                    System.out.println("Enter the path to save the decoded details file (e.g., output/qrcode_read_details.txt):");
                    String readDetailsFilePath = scanner.nextLine();
                    
                    try {
                        String decodedText = readQRCode(readFilePath);
                        System.out.println("Decoded text: " + decodedText);
                        
                        // Write details to file
                        try (FileOutputStream fos = new FileOutputStream(readDetailsFilePath, true)) {
                            fos.write(("Decoded QR Code content: " + decodedText + "\n").getBytes());
                        }
                    } catch (IOException | NotFoundException e) {
                        System.err.println("Could not read QR Code: " + e.getMessage());
                    }
                }
                    
                default -> System.out.println("Invalid choice. Please choose 1 or 2.");
            }
        }
    }

//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }
}
