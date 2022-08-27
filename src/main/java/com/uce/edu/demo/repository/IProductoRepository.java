package com.uce.edu.demo.repository;

import com.uce.edu.demo.repository.modelo.Producto;
import com.uce.edu.demo.repository.modelo.ProductoStock;

public interface IProductoRepository {

public void insertar(Producto producto);
	
	public void actulizar(Producto producto);
	
	public Producto buscarCodigoBarras(String codigoBarras);
	
	public Producto busquedaStockCodigo(String codigoBarras);
	
}
