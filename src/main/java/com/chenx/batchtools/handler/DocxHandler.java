package com.chenx.batchtools.handler;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 * 
 * 
 * @author chenx
 * @since 2019-01-08 09:47:37
 * @version 1.0.0
 *
 */
public class DocxHandler implements  Handler {

	@Override
	public List<String> exec(String filename) throws Exception {
		
        List<String> paraText = new ArrayList<String>();

        File file = null;
        FileInputStream fis = null;
        XWPFDocument document = null;
        XWPFNumbering numbering = null;
        XWPFParagraph para = null;
        XWPFNum num = null;
        List<XWPFParagraph> paraList = null;
        Iterator<XWPFParagraph> paraIter = null;
        BigInteger numID = null;
        int numberingID = -1;
        try {
            file = new File(filename);
            fis = new FileInputStream(file);
            document = new XWPFDocument(fis);
            
            fis.close();
            fis = null;
            
            numbering = document.getNumbering();
            
            paraList = document.getParagraphs();
            paraIter = paraList.iterator();
            
            boolean preIsSubGraph  = false;
            
            while(paraIter.hasNext()) {
                para = paraIter.next();
                numID = para.getNumID();
                if(numID != null) {
                    if(numID.intValue() != numberingID) {
                        num = numbering.getNum(numID);
                        numberingID = numID.intValue();
                        System.out.println("Getting details of the new numbering system " + numberingID);
                        System.out.println("It's abstract numID is " + num.getCTNum().getAbstractNumId().getVal().intValue());
                    }
                    else {
                        System.out.println("Iterating through the numbers， numID="  + numID);
                    }
                    paraText.add(para.getParagraphText());
                    
                }
                else {
                    System.out.print("Null numID ");
                    int index  = paraText.size() - 1;
                    String element = paraText.get(index);
                    element += ("\n" + para.getParagraphText());
                    paraText.set(index, element);
                }
 
                System.out.println("Text " + para.getParagraphText());
               
            }
        }
        finally {
            if(fis != null) {
                fis.close();
                fis = null;
            }
        }
        return paraText;
	}

}
