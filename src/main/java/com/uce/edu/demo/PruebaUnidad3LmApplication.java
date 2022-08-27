package com.uce.edu.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import org.jboss.logging.Logger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uce.edu.demo.repository.modelo.Producto;
import com.uce.edu.demo.repository.modelo.ProductoIngreso;
import com.uce.edu.demo.service.IGestorService;
import com.uce.edu.demo.service.IProductoService;

@SpringBootApplication
public class PruebaUnidad3LmApplication implements CommandLineRunner {

	private static Logger LOG = Logger.getLogger(PruebaUnidad3LmApplication.class);

	@Autowired
	private IProductoService iProductoService;

	@Autowired
	private IGestorService iGestorService;

	public static void main(String[] args) {
		SpringApplication.run(PruebaUnidad3LmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		Producto p1 = new Producto();

		p1.setCategoria("vestimenta");
		p1.setCodigoBarras("ab1");
		p1.setNombre("pantalon");
		p1.setPrecio(new BigDecimal(25));
		p1.setStock(15);

		Producto p2 = new Producto();

		p2.setCategoria("vestimenta");
		p2.setCodigoBarras("ab1");
		p2.setNombre("pantalon");
		p2.setPrecio(new BigDecimal(25));
		p2.setStock(50);

		this.iProductoService.insertar(p1);
		this.iProductoService.insertar(p2);
		
		
		Producto consulta = this.iProductoService.buscarCodigoBarras("ab1");
		
		LOG.info("Consulta simple codigo de barras: " + consulta);
		

		ProductoIngreso ingreso = new ProductoIngreso();
		ingreso.setCantidad(10);
		ingreso.setCodigoBarras("ab1");
		
		

		List<ProductoIngreso> listaVenta = new ArrayList<ProductoIngreso>();
		listaVenta.add(ingreso);

		this.iGestorService.realizarVenta(listaVenta, "1717441180", "002");

		LOG.info("Ventas realizadas: "
				+ this.iGestorService.buscaPorFecha(LocalDateTime.of(2022, 8, 26, 19, 37), "vestimenta", 5));

		// 2022-08-26 19:37:41.972966

		LOG.info("Consulta Stock: " + this.iProductoService.consultaStock("ab1"));
	}

}
