package com.smartcontactmanager.Helper;

// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Component;
// import org.springframework.web.multipart.MultipartFile;

// import com.smartcontactmanager.Entity.p;
// import com.smartcontactmanager.Entity.User;
// import com.smartcontactmanager.services.UserServices;

// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;

// @Component
// public class HelperExl {

//     @Autowired
//     UserServices userService;

//     // check that file is of excel type or not
//     public static boolean checkExcelFormat(MultipartFile file) {

//         String contentType = file.getContentType();
//         if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//             return true;
//         } else {
//             return false;
//         }

//     }

//     // convert excel to list of products

//     public List<p> convertExcelToListOfProduct(InputStream is, Authentication authentication) {

//         String username = CurrentUser.getEmailWithLoggedUser(authentication);
//         User user = userService.getUserByEmail(username);

//         List<p> list = new ArrayList<>();

//         try {
//             // XSSFWorkbook workbook = new XSSFWorkbook(is);
//             XSSFWorkbook workbook = new XSSFWorkbook(is);

//             // Debugging: Print all sheet names
//             int numberOfSheets = workbook.getNumberOfSheets();
//             for (int i = 0; i < numberOfSheets; i++) {
//                 System.out.println("Sheet name: " + workbook.getSheetName(i));
//             }

//             // Use the actual sheet name from the Excel file
//             XSSFSheet sheet = workbook.getSheet("Sheet 1"); // Update this based on actual sheet name

//             // Check if the sheet exists
//             if (sheet == null) {
//                 throw new IllegalArgumentException("Sheet 'Sheet 1' not found in the Excel file");
//             }

//             int rowNumber = 0;
//             Iterator<Row> iterator = sheet.iterator();

//             while (iterator.hasNext()) {
//                 Row row = iterator.next();

//                 if (rowNumber == 0) {
//                     rowNumber++;
//                     continue;
//                 }

//                 Iterator<Cell> cells = row.iterator();
//                 int cid = 0;

//                 p p = new p();

//                 while (cells.hasNext()) {
//                     Cell cell = cells.next();

//                     switch (cid) {
//                         case 0:
//                             p.setName(cell.getStringCellValue());
//                             break;
//                         case 1:
//                             p.setEmail(cell.getStringCellValue());
//                             break;
//                         case 2:
//                             p.setPhoneNumber(cell.getStringCellValue());
//                             break;
//                         // case 3:
//                         // p.setFavourite(cell.getStringCellValue());
//                         // break;
//                         default:
//                             break;
//                     }
//                     cid++;
//                 }

//                 p.setUser(user);

//                 list.add(p);
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return list;
//     }

// }

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.Entity.Product;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {

    // check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }

    // convert excel to list of products

    public static List<Product> convertExcelToListOfProduct(InputStream is) {
        List<Product> list = new ArrayList<>();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet(workbook.getSheetName(0));

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet 'Sheet 1' not found in the Excel file");
            }

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int cid = 0;

                Product p = new Product();

                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {

                        case 0:
                            p.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            p.setEmail(cell.getStringCellValue());
                            break;
                        case 2:
                            p.setPhoneNumber(cell.getStringCellValue());
                            break;
                        // case 3:
                        // p.setFavourite(cell.getStringCellValue());
                        // break;
                        default:
                            break;
                    }
                    cid++;

                }

                list.add(p);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

}
