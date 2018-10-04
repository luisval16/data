package com.orasoft.data.link.view.xlsx;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.orasoft.data.link.models.entity.SyncHistory;
import com.orasoft.data.link.models.service.ISyncHistoryService;

@Component("history.xlsx")
public class SyncHistoryXlsxView extends AbstractXlsxView{
	
	@Autowired
	ISyncHistoryService syncService;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		List<SyncHistory> syncHistory = syncService.findAll();
		
		Sheet sheet = workbook.createSheet();
		
		Row row = sheet.createRow(0);
		Cell cell =  row.createCell(0);
		
		cell.setCellValue("id");
		cell = row.createCell(1);
		cell.setCellValue("body");
		cell = row.createCell(2);
		cell.setCellValue("tipo");
		cell = row.createCell(3);
		cell.setCellValue("entidad");
		cell = row.createCell(4);
		cell.setCellValue("fecha");
		
		for(int i = 0 ; i < syncHistory.size(); i++) {
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(syncHistory.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(syncHistory.get(i).getBody());
			cell = row.createCell(2);
			cell.setCellValue(syncHistory.get(i).getTipo());
			cell = row.createCell(3);
			cell.setCellValue(syncHistory.get(i).getEntidad());
			cell = row.createCell(4);
			
			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(syncHistory.get(i).getFecha()));
		}
		
		
		
	}

}
