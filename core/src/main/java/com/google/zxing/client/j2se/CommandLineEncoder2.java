/*
 * Copyright 2011 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.j2se;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;
//import com.google.zxing.WriterException;
//import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
//import com.google.zxing.client.j2se.MatrixToImageWriter;


//import com.beust.jcommander.JCommander;
//import java.io.BufferedOutputStream;
import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import java.nio.file.FileSystems;
//import java.nio.file.Path;
import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.file.Files;
import java.util.EnumMap;
//import java.util.Locale;
import java.util.Map;
//import java.util.ArrayList;
import java.util.Base64;
//import java.util.stream.Stream;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.HashMap;


//import java.lang.String;
//import org.apache.commons.codec.binary.Hex;
//import java.nio.charset.StandardCharsets;
//import java.lang.StringBuilder;

//import java.io.*;
//import java.net.*;


/**
 * Command line utility for encoding barcodes.
 * 
 * @author Sean Owen
 */
public final class CommandLineEncoder2 {

  private CommandLineEncoder2() {
  }

  static byte [] readhexfile(String s1)throws FileNotFoundException, IOException {
    //s1 = "c:\\Temp\\prueba4.hex";
    System.out.println("filename: " + s1);
    File infilehex = new File(s1);
    int infilehexsz = (int) infilehex.length();
    System.out.println("filesize: " + infilehexsz);
    byte [] ba1 = new byte [ infilehexsz ];
    FileInputStream instream1 = new FileInputStream(infilehex);
    instream1.read(ba1);
    return ba1;
  }

  static void main2(String s1) throws Exception {
    String parameterName = "c:\\Temp\\parameters.txt";
    //String config_basefilename = "";
    String config_width_s = "";
    String config_height_s = "";
    String config_height2_s = "";
    String config_columns_s = "";
    String config_imageFormat = "BMP";
    BarcodeFormat config_barcodeFormat = BarcodeFormat.PDF_417;
    String config_errorCorrectionLevel = "1";

    try {
      BufferedReader reader1 = new BufferedReader(new FileReader(parameterName));
      //config_basefilename = reader1.readLine();
      config_width_s = reader1.readLine();
      config_height_s = reader1.readLine();
      config_height2_s = reader1.readLine();
      config_columns_s = reader1.readLine();
      reader1.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    int config_width = 0;
    int config_height = 0;
    int config_height2 = 0;
    int config_columns = 0;
    try {
      config_width = Integer.parseInt(config_width_s);
      config_height = Integer.parseInt(config_height_s);
      config_height2 = Integer.parseInt(config_height2_s);
      config_columns = Integer.parseInt(config_columns_s);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    int separator = s1.indexOf('|');
    String config_basefilename = s1.substring(separator + 1);
    s1 = s1.substring(0,separator);
    System.out.println("filenames: [" + s1 + "], [" + config_basefilename + "]");
    byte [] ba1 = readhexfile(s1);
    int infilehexsz = ba1.length;
    System.out.println("Size of hexa file: " + infilehexsz);
    if (infilehexsz % 2 == 1) {
      System.out.println("Error en el archivo hexa");
    }
    StringBuilder strb1 = new StringBuilder(infilehexsz / 2);
    for (int i5 = 0; i5 < ba1.length; i5 += 2) {
      boolean goodhex = true;
      int l = 0;
      int m = 0;
      int j1 = ba1[i5];
      int k1 = ba1[i5 + 1];
      if (j1 > (48 - 1) && j1 < (48 + 10)) {
        l = j1 - 48;
      } else if (j1 > (65 - 1) && j1 < (65 + 7)) {
        l = j1 - 65 + 10;
      } else if (j1 > (97 - 1) && j1 < (97 + 7)) {
        l = j1 - 97 + 10;
      } else {
        goodhex = false;
        System.out.println("error");
      }
      if (k1 > (48 - 1) && k1 < (48 + 10)) {
        m = k1 - 48;
      } else if (k1 > (65 - 1) && k1 < (65 + 7)) {
        m = k1 - 65 + 10;
      } else if (k1 > (97 - 1) && k1 < (97 + 7)) {
        m = k1 - 97 + 10;
      } else {
        goodhex = false;
        System.out.println("error");
      }
      int n = l * 16 + m;
      char nc = (char) n;
      byte nb = (byte) n;
      //System.out.println("values[" + i5 + "]: " + l + " " + m + " " + nc);
      if (goodhex) {
        strb1.append(nc);
      }
      //System.out.println("input so far: [" + strb1.toString() + "]");
    }
    String strf = strb1.toString();

    Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
    Dimensions dimensions = new Dimensions(config_columns, config_columns, 1, 1000);
    hints.put(EncodeHintType.ERROR_CORRECTION, config_errorCorrectionLevel);
    //hints.put(com.google.zxing.pdf417.encoder.PDF417Common.ERROR_CORRECTION, config.errorCorrectionLevel);
    hints.put(com.google.zxing.EncodeHintType.PDF417_DIMENSIONS, dimensions);
    hints.put(com.google.zxing.EncodeHintType.PDF417_COMPACTION, Compaction.AUTO);
    // AUTO, NUMERIC, BYTE, TEXT

    BitMatrix matrix = new MultiFormatWriter().encode(
        strf, config_barcodeFormat, config_width,
        config_height2, hints);
    String fileString1 = config_basefilename + ".bmp";
    String fileString2 = config_basefilename + "x.bmp";
    String fileString3 = config_basefilename + "y.bmp";
    MatrixToImageWriter.writeToPath(matrix, config_imageFormat,
        Paths.get(fileString1));
    File infile = new File(fileString1);
    byte [] ba = new byte [ (int) infile.length() ];
    FileOutputStream outstream;
    try (FileInputStream instream = new FileInputStream(infile)) {
      instream.read(ba);
      System.out.println("Resolution: " + ba[0x26] + " " + ba[0x27] + " "
          + ba[0x2a] + " " + ba[0x2b]);
      ba[0x26] = (byte) 0x8c;
      ba[0x27] = (byte) 0xb8;
      ba[0x2a] = (byte) 0x8c;
      ba[0x2b] = (byte) 0xb8;
      //instream.close();
      File file2 = new File(fileString2);
      FileOutputStream stream2 = new FileOutputStream(file2);
      stream2.write(ba);
      stream2.close();
      String geometry1 = config_width_s + "x" + config_height_s + "!";
      System.out.println("geometry: " + geometry1);
      String command1[] = { };
      /*  {
        "C:\\Program Files\\ImageMagick-7.1.1-Q16-HDRI\\magick.exe",
        fileString2,
        "-resize",
        geometry1,
        fileString3 }; */
      try {
        Process process = Runtime.getRuntime().exec(command1);
        int exitValue = process.waitFor();
        System.out.println("exit value: " + exitValue);
      } catch (IOException | InterruptedException ex) {
        System.out.println("problem executing magick");
      }
      File file3 = new File(fileString1);
      byte [] ba2 = new byte [ (int) file3.length() ];
      try (FileInputStream stream3 = new FileInputStream(file3)) {
        stream3.read(ba2);
      } catch (IOException ex) {
        System.out.println("problem reading scaled bmp");
      }
      String encoded = Base64.getEncoder().encodeToString(ba2);
      File outfile = new File(fileString1 + ".b64");
      outstream = new FileOutputStream(outfile);
      outstream.write(encoded.getBytes());
    }
    outstream.close();
  }

  public static void main(String [] args) throws Exception {
    try {
      //!!!byte [] b1 = new byte [1024];
      ServerSocket ssoc;
      ssoc = new ServerSocket(4379);
      boolean ends = false;
      while (!ends) {
        System.out.println("waiting to accept");
        /*!!!
        Socket soc = ssoc.accept();
        System.out.println("waiting for message");
        InputStream is = soc.getInputStream();
        OutputStream os = soc.getOutputStream();
        BufferedOutputStream bos;
        bos = new BufferedOutputStream(os);
        int r1 = is.read(b1);
        StringBuilder sb1 = new StringBuilder(r1);
        for (int i=0; i<r1; i++){
          char c1 = (char)b1[i];
          sb1.append(c1);
        }
        String s1 = sb1.toString();
        !!!*/
        String s1 = "C:\\Temp\\prueba9.hex|C:\\Temp\\prueba9";
        int r1 = s1.length();
        System.out.println("received message: [" + s1 + "] length: " + s1.length());
        if ("stop".equals(s1)) {
          ends = true;
        }
        System.out.println("read " + r1 + " bytes");
        System.out.println("size of s1 is " + s1.length());
        System.out.println("[" + s1 + "]");
        main2(s1);
        System.out.println("sending response");
        /*!!!
        String s = "server response to: " + s1;
        byte [] b = s.getBytes();
        bos.write(b);
        bos.flush();
        bos.close();
        is.close();
        soc.close();
        !!!*/
        ends = true;
      }
      ssoc.close();
    } catch (IOException e) {
      System.out.println(e);
    }
    System.out.println("done");
  }
}
