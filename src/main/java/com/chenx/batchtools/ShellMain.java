package com.chenx.batchtools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.chenx.batchtools.handler.DocxHandler;
import com.chenx.batchtools.handler.Handler;
import com.chenx.batchtools.util.ChineseNumberConverter;

/**
 * 主界面程序
 * 
 * @author chenx
 * @since 2019-01-07 20:29:41
 * @version 1.0.0
 * 
 */

public class ShellMain {

	protected Shell shellMain;
	private Text textDirectory;
	private Text text;
	
	private ArrayList<String> docfiles;
	
	private ArrayList<String> excelfiles;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ShellMain window = new ShellMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shellMain.open();
		shellMain.layout();
		while (!shellMain.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shellMain = new Shell();
		shellMain.setSize(450, 300);
		shellMain.setText("Word2ExcelBatchTool");
		shellMain.setLayout(new GridLayout(2, false));
		
		Menu menu = new Menu(shellMain, SWT.BAR);
		shellMain.setMenuBar(menu);
		
		MenuItem mntmAbout = new MenuItem(menu, SWT.NONE);

		mntmAbout.setText("About");
		
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shellMain,  SWT.CANCEL | SWT.OK);
			    messageBox.setText("About");
			    messageBox.setMessage("Any  question, please contact to chenxofhit@gmail.com !");
			    messageBox.open();
			}
		});
		
		Label lblHelp = new Label(shellMain, SWT.NONE);
		lblHelp.setText("请选择包含 word 文件的文件夹:");
		new Label(shellMain, SWT.NONE);
		
		Label label_1 = new Label(shellMain, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		textDirectory = new Text(shellMain, SWT.BORDER);
		GridData gd_textDirectory = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_textDirectory.heightHint = 20;
		gd_textDirectory.widthHint = 339;
		textDirectory.setLayoutData(gd_textDirectory);
		
		Button btnBrowse = new Button(shellMain, SWT.CENTER);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    DirectoryDialog dialog = new DirectoryDialog(shellMain);

	            String fn = dialog.open();
	            if (fn != null) {
	            	textDirectory.setText(fn);
        			text.setText("");

	            	File file = new File(fn);
	        		File files[] = file.listFiles(new DocFilenameFilter());
	        		int len = files.length;
	        		if(len  > 0) {
	        			docfiles = new  ArrayList<String>();
	        			for(File f: files){
	        				docfiles.add(f.getAbsolutePath());
	        				text.append("发现 word 文档："+ f.getAbsolutePath() + "\r\n");
	        			}
        				text.append("总计发现 word 文档："+ len +  "个，请点击 开始 按钮进行转换 \r\n");
	        		}else {
        				text.append("没有找到 word 文档，请重新选择文件夹 \r\n");
	        		}
    				text.append("----------------------------------------------------------------------------------------------\r\n");
	            }
			}
		});
		btnBrowse.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		btnBrowse.setText("浏览");
		
		Label label = new Label(shellMain, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		Button btnGo = new Button(shellMain, SWT.CENTER);
		btnGo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				for (String f : docfiles) {
					
			        File file = new File(f);
			        try {
			            FileInputStream fis = new FileInputStream(file);
			            
			            if(f.endsWith(".doc")) {
			            	HWPFDocument doc = new HWPFDocument(fis);
			            	String docText = doc.getDocumentText();
			            	System.out.println(docText);
			            }else if(f.endsWith(".docx")) {
//			            	XWPFDocument xdoc = new XWPFDocument(fis);
//			                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
//			                String doc1 = extractor.getText();
//			                System.out.println(doc1);
			                Handler docxHanlder =  new DocxHandler();
			                List<String> paraTexts = docxHanlder.exec(f);
			                

			                String excelFileName = f  + ".xlsx";// name of excel file
			             
			                String sheetName = "Sheet1";// name of sheet
			             
			                XSSFWorkbook wb = new XSSFWorkbook();
			                XSSFSheet sheet = wb.createSheet(sheetName);
			             
			                // iterating r number of rows
			                for (int r = 0; r < paraTexts.size(); r++)
			                {
			                  XSSFRow row = sheet.createRow(r);
//			                  row.setHeight((short)-1);
//			                  // iterating c number of columns
//			                  for (int c = 0; c < 5; c++)
//			                  {
//			                    XSSFCell cell = row.createCell(c);
//			             
//			                    cell.setCellValue("Cell " + r + " " + c);
//			                  }
			                  
			                  XSSFCell cell = row.createCell(0);
			                  CellStyle cs = wb.createCellStyle();
			                  cs.setWrapText(true);
			                  cell.setCellStyle(cs);
			                  cell.setCellValue("第" + ChineseNumberConverter.convert(r+1)+"条"); 
			                
			                  cell = row.createCell(1);
			                  cs = wb.createCellStyle();
			                  cs.setWrapText(true);
			                  cell.setCellStyle(cs);
			                  cell.setCellValue(paraTexts.get(r));
			                  
			                }
			             
			                FileOutputStream fileOut = new FileOutputStream(excelFileName);
			             
			                // write this workbook to an Outputstream.
			                wb.write(fileOut);
			                fileOut.flush();

			                text.append("恭喜，输出到文件：" + excelFileName+ "成功！\r\n");
			                
			            }
			            fis.close();
			        } catch (Exception ecpt) {
			        	ecpt.printStackTrace();
			        }
				}
			}
		});
		GridData gd_btnGo = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_btnGo.widthHint = 438;
		btnGo.setLayoutData(gd_btnGo);
		btnGo.setText("开始!");
		
		text = new Text(shellMain, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_text.heightHint = 135;
		text.setLayoutData(gd_text);

	}

}
