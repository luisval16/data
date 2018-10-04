package com.orasoft.data.link.models.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.orasoft.data.link.models.entity.SyncHistory;

public interface ISyncHistoryDao extends PagingAndSortingRepository<SyncHistory, Long> {

	@Query("select s from SyncHistory s where s.entidad like %?1%")
	public Page<SyncHistory> findByEntidadLike(String entidad, Pageable pageable);

	@Query("select s from SyncHistory s where s.fecha between :start and :end order by s.id desc")
	public Page<SyncHistory> findByFechaBetween(@Param("start") @Temporal(TemporalType.TIMESTAMP) Date fecha1,
			@Param("end") @Temporal(TemporalType.TIMESTAMP) Date fecha2, Pageable pageable);

	@Query("select COUNT(s) from SyncHistory s where s.entidad like %?1%")
	public Long CountByEntidadLike(String entidad);

	@Query("select COUNT(s) from SyncHistory s where tipo = ?1 and s.entidad like %?2%")
	public Long CountByTipoAndEntidadLike(String tipo, String entidad);

	@Query("select COUNT(s) from SyncHistory s where tipo = ?1 and s.entidad like %?2% and s.entidad like %?3%")
	public Long CountByTipoAndEntidadLike(String tipo, String entidad, String table);

	@Query("select COUNT(s) from SyncHistory s where s.entidad like %?1% and s.fecha between ?2 and ?3")
	public Long CountByEntidadLikeAndFechaBetween(String entidad, @Temporal(TemporalType.TIMESTAMP) Date fecha1,
			@Temporal(TemporalType.TIMESTAMP) Date fecha2);

	@Query("select COUNT(s) from SyncHistory s where tipo = ?1 and s.entidad like %?2% and s.fecha between ?3 and ?4")
	public Long CountByTipoAndEntidadLikeAndFechaBetween(String tipo, String entidad,
			@Temporal(TemporalType.TIMESTAMP) Date fecha1, @Temporal(TemporalType.TIMESTAMP) Date fecha2);

	@Query("select COUNT(s) from SyncHistory s where tipo = ?1 and s.entidad like %?2% and s.entidad like %?3% and s.fecha between ?4 and ?5")
	public Long CountByTipoAndEntidadLikeAndFechaBetween(String tipo, String entidad, String table,
			@Temporal(TemporalType.TIMESTAMP) Date fecha1, @Temporal(TemporalType.TIMESTAMP) Date fecha2);

}
