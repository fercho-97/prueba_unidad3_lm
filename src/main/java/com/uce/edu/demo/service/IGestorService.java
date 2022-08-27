package com.uce.edu.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.uce.edu.demo.repository.modelo.ProductoIngreso;
import com.uce.edu.demo.repository.modelo.VentaReporte;

public interface IGestorService {

	public void realizarVenta(List<ProductoIngreso> lista, String cedula, String numeroVenta);
	
	public List<VentaReporte> buscaPorFecha(LocalDateTime fecha, String categoria, Integer cantidad);
}
