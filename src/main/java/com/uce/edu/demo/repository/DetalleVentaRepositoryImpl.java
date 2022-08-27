package com.uce.edu.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Repository;

import com.uce.edu.demo.repository.modelo.DetalleVenta;

@Repository
@Transactional
public class DetalleVentaRepositoryImpl  implements IDetalleVentaRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<DetalleVenta> buscaPorFecha(LocalDateTime fechaVenta) {
		// TODO Auto-generated method stub
		TypedQuery<DetalleVenta> myquery = this.entityManager.createQuery(
				"SELECT d FROM DetalleVenta d JOIN d.venta v WHERE v.fecha >= :datoFecha ", DetalleVenta.class);
		myquery.setParameter("datoFecha", fechaVenta);

		List<DetalleVenta> detalleVentas = myquery.getResultList();

		for (DetalleVenta detalleVenta : detalleVentas) {

			detalleVenta.getProducto().getCategoria();

		}

		return detalleVentas;
	}

}
