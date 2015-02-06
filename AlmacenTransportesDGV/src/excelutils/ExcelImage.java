/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelutils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

/**
 *
 * @author David
 */
public class ExcelImage {
    
    protected int topLeftColumn;
    protected int topUpperRow;
    
    public ExcelImage(){
        
    }
    
    public ExcelImage(int row, int column){
        topLeftColumn = column;
        topUpperRow = row;
    }
    
    public void insertImage(String path, String sName, Workbook wb, FileOutputStream fileOut) {
        try {
            InputStream inputStream = new FileInputStream(path);
            Sheet sheet = wb.getSheet(sName);
            byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream(path));
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();
            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            
            anchor.setCol1(topLeftColumn);
            anchor.setRow1(topUpperRow);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();
            wb.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
}
