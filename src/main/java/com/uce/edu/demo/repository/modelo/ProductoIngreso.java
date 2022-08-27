package com.uce.edu.demo.repository.modelo;

public class ProductoIngreso {

	private String codigoBarras;

	private Integer cantidad;

	public ProductoIngreso() {

	}

	public ProductoIngreso(String codigoBarras, Integer cantidad) {
		super();
		this.codigoBarras = codigoBarras;
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ProductoIngreso [codigoBarras=" + codigoBarras + ", cantidad=" + cantidad + "]";
	}

	// SET Y GET
	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}
