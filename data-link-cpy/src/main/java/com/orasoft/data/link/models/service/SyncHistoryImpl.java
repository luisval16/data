package com.orasoft.data.link.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orasoft.data.link.models.dao.ISyncHistoryDao;
import com.orasoft.data.link.models.entity.SyncHistory;

@Service
public class SyncHistoryImpl implements ISyncHistoryService{

	@Autowired
	private ISyncHistoryDao syncHistoryDao;
	
	@Override
	public List<SyncHistory> findAll() {
		// TODO Auto-generated method stub
		return (List<SyncHistory>) syncHistoryDao.findAll();
	}

	@Override
	public SyncHistory save(SyncHistory syncHistory) {
		// TODO Auto-generated method stub
		return syncHistoryDao.save(syncHistory);
	}

	@Override
	public SyncHistory findOne(Long id) {
		// TODO Auto-generated method stub
		return syncHistoryDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		syncHistoryDao.deleteById(id);
	}

	@Override
	public Page<SyncHistory> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return syncHistoryDao.findAll(pageable);
	}

	@Override
	public Page<SyncHistory> findByEntidadLike(String entidad,Pageable pageable) {
		// TODO Auto-generated method stub
		return syncHistoryDao.findByEntidadLike(entidad ,pageable);
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return syncHistoryDao.count();
	}

	@Override
	public Long CountByEntidadLike(String entidad) {
		// TODO Auto-generated method stub
		return syncHistoryDao.CountByEntidadLike(entidad);
	}

	@Override
	public Long CountByTipoAndEntidadLike(String tipo, String entidad) {
		// TODO Auto-generated method stub
		return syncHistoryDao.CountByTipoAndEntidadLike(tipo, entidad);
	}

	@Override
	public Long CountByTipoAndEntidadLike(String tipo, String entidad, String table) {
		// TODO Auto-generated method stub
		return syncHistoryDao.CountByTipoAndEntidadLike(tipo, entidad, table);
	}

	@Override
	public Page<SyncHistory> findByFechaBetween(Date fecha1, Date fecha2, Pageable pageable) {
		// TODO Auto-generated method stub
		return this.syncHistoryDao.findByFechaBetween(fecha1, fecha2,pageable);
	}

	@Override
	public Long CountByEntidadLikeAndFechaBetween(String entidad, Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return this.syncHistoryDao.CountByEntidadLikeAndFechaBetween(entidad, fecha1, fecha2);
	}

	@Override
	public Long CountByTipoAndEntidadLikeAndFechaBetween(String tipo, String entidad, Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return this.syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween(tipo, entidad, fecha1, fecha2);
	}

	@Override
	public Long CountByTipoAndEntidadLikeAndFechaBetween(String tipo, String entidad, String table, Date fecha1,
			Date fecha2) {
		// TODO Auto-generated method stub
		return this.syncHistoryDao.CountByTipoAndEntidadLikeAndFechaBetween(tipo, entidad, table, fecha1, fecha2);
	}

	

}
