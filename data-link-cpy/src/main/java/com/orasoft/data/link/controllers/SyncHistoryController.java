package com.orasoft.data.link.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.orasoft.data.link.models.entity.SyncHistory;
import com.orasoft.data.link.models.service.ISyncHistoryService;
import com.orasoft.data.link.util.paginator.PageRender;

@Controller
public class SyncHistoryController {
	
	@Autowired
	private ISyncHistoryService syncHistoryDao;
	
	@RequestMapping(value="/history",method=RequestMethod.GET)
	public String listar(@RequestParam(name="page",defaultValue="0") int page,
			@RequestParam(name="from", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
			@RequestParam(name="to",required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate,
			Model model) throws ParseException{
		System.err.println(fromDate + " - " + toDate );
		Pageable pageRequest = PageRequest.of(page, 10);
		if (fromDate == null || toDate == null) {
			Calendar calFirstDay = Calendar.getInstance();
			calFirstDay.set(Calendar.DAY_OF_MONTH,1);
			fromDate = calFirstDay.getTime();
			
			Calendar calLastDay = Calendar.getInstance();
			calLastDay.set(Calendar.DAY_OF_MONTH,calLastDay.getActualMaximum(Calendar.DAY_OF_MONTH));
			toDate = calLastDay.getTime();
			
		}
		
		Page<SyncHistory> list = this.syncHistoryDao.findByFechaBetween(fromDate,toDate,pageRequest);
				//syncHistoryDao.findAll(pageRequest);
		//syncHistoryDao.findByEntidadLike("connectwise", pageRequest);
		
		PageRender<SyncHistory> pageRender =  new PageRender<>("/history", list);
		
		model.addAttribute("titulo","Data Link Synchronization History");
		//List<SyncHistory> list =  syncHistoryDao.findAll();
		for(SyncHistory obj : list) {
			obj.setDataObject(obj.getBody());
			obj.setAppAndTabbla();
		}
		model.addAttribute("from",new SimpleDateFormat("yyyy-MM-dd").format(fromDate));
	    model.addAttribute("to",new SimpleDateFormat("yyyy-MM-dd").format(toDate));
		/*model.addAttribute("cw",syncHistoryDao.CountByEntidadLike("connectwise"));
	    model.addAttribute("is",syncHistoryDao.CountByEntidadLike("infusionsoft"));
	    model.addAttribute("cw",syncHistoryDao.CountByEntidadLike("connectwise"));
	    model.addAttribute("is",syncHistoryDao.CountByEntidadLike("infusionsoft"));
	    model.addAttribute("cwInsert",syncHistoryDao.CountByTipoAndEntidadLike("insert", "connectwise"));
	    model.addAttribute("isInsert",syncHistoryDao.CountByTipoAndEntidadLike("insert", "infusionsoft"));
	    model.addAttribute("cwEdit",syncHistoryDao.CountByTipoAndEntidadLike("edit", "connectwise"));
	    model.addAttribute("isEdit",syncHistoryDao.CountByTipoAndEntidadLike("edit", "infusionsoft"));
	    model.addAttribute("cwConIn",syncHistoryDao.CountByTipoAndEntidadLike("insert", "connectwise","contacts"));
	    model.addAttribute("cwComIn",syncHistoryDao.CountByTipoAndEntidadLike("insert", "connectwise","companies"));
	    model.addAttribute("cwOppIn",syncHistoryDao.CountByTipoAndEntidadLike("insert", "connectwise","opportunities"));
	    model.addAttribute("cwOppEd",syncHistoryDao.CountByTipoAndEntidadLike("edit", "connectwise","opportunities"));
	    model.addAttribute("isConIn",syncHistoryDao.CountByTipoAndEntidadLike("insert", "infusionsoft","contacts"));
	    model.addAttribute("isComIn",syncHistoryDao.CountByTipoAndEntidadLike("insert", "infusionsoft","companies"));
	    model.addAttribute("isOppIn",syncHistoryDao.CountByTipoAndEntidadLike("insert", "infusionsoft","opportunities"));
	    model.addAttribute("isOppEd",syncHistoryDao.CountByTipoAndEntidadLike("edit", "infusionsoft","opportunities"));*/
	    model.addAttribute("cw",syncHistoryDao.CountByEntidadLikeAndFechaBetween("connectwise",fromDate,toDate));
	    model.addAttribute("is",syncHistoryDao.CountByEntidadLikeAndFechaBetween("infusionsoft",fromDate,toDate));
	    model.addAttribute("cw",syncHistoryDao.CountByEntidadLikeAndFechaBetween("connectwise",fromDate,toDate));
	    model.addAttribute("is",syncHistoryDao.CountByEntidadLikeAndFechaBetween("infusionsoft",fromDate,toDate));
	    model.addAttribute("cwInsert",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "connectwise",fromDate,toDate));
	    model.addAttribute("isInsert",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "infusionsoft",fromDate,toDate));
	    model.addAttribute("cwEdit",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("edit", "connectwise",fromDate,toDate));
	    model.addAttribute("isEdit",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("edit", "infusionsoft",fromDate,toDate));
	    model.addAttribute("cwConIn",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "connectwise","contacts",fromDate,toDate));
	    model.addAttribute("cwComIn",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "connectwise","companies",fromDate,toDate));
	    model.addAttribute("cwOppIn",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "connectwise","opportunities",fromDate,toDate));
	    model.addAttribute("cwOppEd",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("edit", "connectwise","opportunities",fromDate,toDate));
	    model.addAttribute("isConIn",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "infusionsoft","contacts",fromDate,toDate));
	    model.addAttribute("isComIn",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "infusionsoft","companies",fromDate,toDate));
	    model.addAttribute("isOppIn",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("insert", "infusionsoft","opportunities",fromDate,toDate));
	    model.addAttribute("isOppEd",syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween("edit", "infusionsoft","opportunities",fromDate,toDate));
		model.addAttribute("syncHistory",list);
		model.addAttribute("page",pageRender);
		return "history";
	}
	
	@RequestMapping(value="/pieprueba",method=RequestMethod.GET)
	public String piePrueba() {
		
		return "test/pieprueba";
	}
	
	/*@GetMapping("/exportxlsx")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {
		List<SyncHistory> syncHistory = syncHistoryDao.findAll();
		model.addAttribute("syncHistory",syncHistory);
		
		return "exportxlsx";
		
	}*/

}
