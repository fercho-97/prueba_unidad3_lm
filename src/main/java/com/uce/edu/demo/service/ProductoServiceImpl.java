package com.uce.edu.demo.service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.edu.demo.PruebaUnidad3LmApplication;
import com.uce.edu.demo.repository.IProductoRepository;
import com.uce.edu.demo.repository.modelo.Producto;
import com.uce.edu.demo.repository.modelo.ProductoStock;

@Service
public class ProductoServiceImpl implements IProductoService{

	private static Logger LOG = Logger.getLogger(ProductoServiceImpl.class);

	
	@Autowired
	private IProductoRepository iProductoRepository;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void insertar(Producto producto) {
		// TODO Auto-generated method stub
		Producto p = new Producto();

		String validacion = "";

		try {

			p = this.iProductoRepository.buscarCodigoBarras(producto.getCodigoBarras());

		} catch (Exception e) {

			this.iProductoRepository.insertar(producto);

			validacion = "l";

		}
			if (validacion.isEmpty()) {
				Producto productoExistente = p;
				productoExistente.setStock(productoExistente.getStock() + producto.getStock());
				this.iProductoRepository.actulizar(productoExistente);
			}	
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void actuliar(Producto producto) {
		// TODO Auto-generated method stub
		this.iProductoRepository.actulizar(producto);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public Producto buscarCodigoBarras(String codigoBarras) {
		// TODO Auto-generated method stub
		return this.iProductoRepository.buscarCodigoBarras(codigoBarras);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public ProductoStock consultaStock(String codigoBarras) {
		// TODO Auto-generated method stub
		
		Producto p = this.iProductoRepository.busquedaStockCodigo(codigoBarras);
		ProductoStock ps = new ProductoStock();
		ps.setCategoria(p.getCategoria());
		ps.setCodigoBarras(p.getCodigoBarras());
		ps.setNombre(p.getNombre());
		ps.setStock(p.getStock());
		
		return ps;
		
		
	}
}
