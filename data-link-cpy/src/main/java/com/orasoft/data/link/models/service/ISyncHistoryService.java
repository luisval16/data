package com.orasoft.data.link.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.orasoft.data.link.models.entity.SyncHistory;

public interface ISyncHistoryService {
	public List<SyncHistory> findAll();
	
	public Page<SyncHistory> findAll(Pageable pageable);
	
	public Page<SyncHistory> findByEntidadLike(String entidad,Pageable pageable);

	public SyncHistory save(SyncHistory syncHistory);

	public SyncHistory findOne(Long id);

	public void delete(Long id);
	
	public Long count();
	
	public Long CountByEntidadLike(String entidad);
	
	
	public Long CountByTipoAndEntidadLike(String tipo, String entidad);
	
	
	public Long CountByTipoAndEntidadLike(String tipo, String entidad, String table);
	
	public Page<SyncHistory> findByFechaBetween(Date fecha1,Date fecha2, Pageable pageable);
	
    public Long CountByEntidadLikeAndFechaBetween(String entidad, Date fecha1, Date fecha2);
	
	
	public Long CountByTipoAndEntidadLikeAndFechaBetween(String tipo, String entidad, Date fecha1, Date fecha2);
	
	
	public Long CountByTipoAndEntidadLikeAndFechaBetween(String tipo, String entidad, String table, Date fecha1, Date fecha2);
}
