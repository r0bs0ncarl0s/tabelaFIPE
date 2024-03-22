package br.com.alura.TabelaFIPE.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import br.com.alura.TabelaFIPE.model.Dados;
import br.com.alura.TabelaFIPE.model.Modelos;
import br.com.alura.TabelaFIPE.model.Veiculo;
import br.com.alura.TabelaFIPE.service.ConsumoAPI;
import br.com.alura.TabelaFIPE.service.ConverteDados;
import br.com.alura.TabelaFIPE.utils.Constantes;

public class Principal {
	
	private Scanner leitura = new Scanner(System.in);
	
	private ConsumoAPI consumo = new ConsumoAPI();
	private ConverteDados conversor = new ConverteDados();
	
	public void exibeMenu(){
		var menu = """
				   " *** OPÇÕES ***"
				   " "
				   " Carro "
				   " Moto " 
				   " Caminhão "
				   " "
				   " Digite uma das opções para consulta:""";
				   
		System.out.println(menu);
		var opcao = leitura.nextLine();
		
		String endereco = "";
		if(opcao.toLowerCase().contains("carr")) {
			endereco = Constantes.URL_API_FIPE + "carros/marcas";
		}else if(opcao.toLowerCase().contains("mot")) {
			endereco = Constantes.URL_API_FIPE + "motos/marcas";
		}else {
			endereco = Constantes.URL_API_FIPE + "caminhoes/marcas";
		}
		
		var json = consumo.obterDados(endereco);
		//System.out.println(json);
		
		var marcas = conversor.obterLista(json, Dados.class);
		
		marcas.stream()
		      .sorted(Comparator.comparing(Dados::nome))
		      .forEach(System.out::println);
		;
		
		System.out.println("\nInforme o código da marca para consulta: ");
		var codigoMarca = leitura.nextLine();
		endereco = endereco + "/" + codigoMarca + "/modelos";
		json = consumo.obterDados(endereco);
		var modeloLista = conversor.obterDados(json, Modelos.class);
		modeloLista.modelos().stream()
							 .sorted(Comparator.comparing(Dados::codigo))
							 .forEach(System.out::println);
		
		System.out.println("\nInforme o código do modelo para consulta: ");
		var codigoModelo = leitura.nextLine();
		endereco = endereco + "/" + codigoModelo + "/anos";
		json = consumo.obterDados(endereco);
		List<Dados> anos = conversor.obterLista(json, Dados.class);
		List<Veiculo> listVeiculos = new ArrayList<>();
		for (int i = 0; i < anos.size(); i++) {
			var enderecoAno = endereco + "/" + anos.get(i).codigo();
			json = consumo.obterDados(enderecoAno);
			Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
			listVeiculos.add(veiculo);
		}
		
		System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
		
		listVeiculos.stream()
					.sorted(Comparator.comparing(Veiculo::valor))
					.forEach(e -> System.out.println(e));
		
		//listVeiculos.forEach(System.out::println);
		//https://parallelum.com.br/fipe/api/v1/caminhoes/marcas/184/modelos/7369/anos/2021-3/
	}	
}
