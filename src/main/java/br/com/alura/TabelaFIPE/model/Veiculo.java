package br.com.alura.TabelaFIPE.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(@JsonAlias("Marca") String marca,
					  @JsonAlias("Valor") String valor,
					  @JsonAlias("Modelo") String modelo,
					  @JsonAlias("AnoModelo") Integer ano,
					  @JsonAlias("Combustivel") String combustivel,
					  @JsonAlias("CodigoFipe") String codFIPE,
					  @JsonAlias("MesReferencia") String mesRef,
					  @JsonAlias("SiglaCombustivel") String siglaCombu) {
}
