package com.uce.edu.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.repository.IDetalleVentaRepository;
import com.uce.edu.demo.repository.IProductoRepository;
import com.uce.edu.demo.repository.IVentaRepository;
import com.uce.edu.demo.repository.modelo.DetalleVenta;
import com.uce.edu.demo.repository.modelo.Producto;
import com.uce.edu.demo.repository.modelo.ProductoIngreso;
import com.uce.edu.demo.repository.modelo.Venta;
import com.uce.edu.demo.repository.modelo.VentaReporte;

@Service
public class GestorServiceImpl implements IGestorService {

	@Autowired
	private IVentaRepository iVentaRepository;

	@Autowired
	private IProductoRepository iProductoRepository;

	@Autowired
	private IDetalleVentaRepository iDetalleVentaRepository;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void realizarVenta(List<ProductoIngreso> lista, String cedula, String numeroVenta) {
		// TODO Auto-generated method stub
		Venta v = new Venta();
		v.setCedulaCliente(cedula);
		v.setNumero(numeroVenta);
		v.setFecha(LocalDateTime.now());

		BigDecimal total = BigDecimal.ZERO;

		List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();

		for (ProductoIngreso productoIngreso : lista) {

			Producto p = this.iProductoRepository.buscarCodigoBarras(productoIngreso.getCodigoBarras());

			if (p.getStock().equals(0)) {

				throw new RuntimeException();
			}
			if (p.getStock() <= productoIngreso.getCantidad()) {
				productoIngreso.setCantidad(p.getStock());
			}
			DetalleVenta dv = new DetalleVenta();
			dv.setProducto(p);
			dv.setPrecioUnitario(p.getPrecio());
			dv.setCantidad(productoIngreso.getCantidad());
			dv.setSubtotal(p.getPrecio().multiply(new BigDecimal(productoIngreso.getCantidad())));

			total = total.add(dv.getSubtotal());
			dv.setVenta(v);
			detalles.add(dv);

			p.setStock(p.getStock() - productoIngreso.getCantidad());
			this.iProductoRepository.actulizar(p);

		}

		v.setDetalles(detalles);
		v.setTotal(total);

		this.iVentaRepository.insertar(v);

	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public List<VentaReporte> buscaPorFecha(LocalDateTime fecha, String categoria, Integer cantidad) {
		// TODO Auto-generated method stub

		List<VentaReporte> reportes = new ArrayList<VentaReporte>();

		List<DetalleVenta> detalles = this.iDetalleVentaRepository.buscaPorFecha(fecha);

		for (DetalleVenta detalle : detalles) {

			// Producto p = dv.getProducto();
			VentaReporte rv = new VentaReporte();

			if (detalle.getCantidad() >= cantidad) {

				Producto p = detalle.getProducto();

				if (p.getCategoria().equals(categoria)) {

					rv.setCantidad(detalle.getCantidad());
					rv.setPrecioUnitario(detalle.getPrecioUnitario());
					rv.setCodigoBarras(p.getCodigoBarras());
					rv.setSubtotal(detalle.getSubtotal());
					rv.setCategoria(p.getCategoria());
					reportes.add(rv);

				}

			}
		}

		return reportes;
	}

}
